package q4.test_coverage.mytests.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import q4.test_coverage.mytests.R
import q4.test_coverage.mytests.databinding.ActivityMainBinding
import q4.test_coverage.mytests.model.SearchResult
import q4.test_coverage.mytests.presenter.search.PresenterSearchContract
import q4.test_coverage.mytests.presenter.search.SearchPresenter
import q4.test_coverage.mytests.repository.GitHubApi
import q4.test_coverage.mytests.repository.GitHubRepository
import q4.test_coverage.mytests.view.details.DetailsActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val adapter = SearchResultAdapter()
    private val presenter: PresenterSearchContract = SearchPresenter(createRepository())
    private var totalCount: Int = 0

    /** В методах жиз. цикла onStart и onStop имплементим методы презентера :*/
    override fun onStart() {
        presenter.onAttach(this)
        super.onStart()
    }

    override fun onStop() {
        presenter.onDetach(this)
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setUI()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setUI() {
        binding.toDetailsActivityButton.setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun setQueryListener() {
        binding.searchEditText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGitHub(query)
                    return@OnEditorActionListener true
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnEditorActionListener false
                }
            }
            false
        })
    }

    private fun createRepository(): GitHubRepository {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        this.totalCount = totalCount
        adapter.updateResults(searchResults)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
