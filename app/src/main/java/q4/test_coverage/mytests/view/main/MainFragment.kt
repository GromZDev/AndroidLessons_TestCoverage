package q4.test_coverage.mytests.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import q4.test_coverage.mytests.R
import q4.test_coverage.mytests.databinding.FragmentMainBinding
import q4.test_coverage.mytests.interactor.MainInterActor
import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.utils.GlideImageLoader
import q4.test_coverage.mytests.utils.networkstatus.isOnline
import q4.test_coverage.mytests.view.base.BaseFragment
import q4.test_coverage.mytests.view.description.DescriptionFragment
import q4.test_coverage.mytests.viewmodel.MainViewModel
import javax.inject.Inject

class MainFragment : BaseFragment<AppState, MainInterActor>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override lateinit var model: MainViewModel

    private val observer = Observer<AppState> {
        renderData(it)
    }

    companion object {
        fun newInstance() = MainFragment()
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "DIALOG_TAG"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {


                val manager = activity?.supportFragmentManager
                manager?.let {
                    manager.beginTransaction()
                        .replace(R.id.fragment_container, DescriptionFragment.newInstance(data))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(inflater, container, false).also {
        _binding = it
        iniViewModel()
        initViews()
    }.root

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //   model.getData("Dictionary", true)

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :

                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    /** Сеть =================================================== */
                    isNetworkAvailable = context?.let { it1 -> isOnline(it1) } == true
                    if (isNetworkAvailable) {
                        model.getData(searchWord, true)

                    } else {
                        showNoInternetConnectionDialog()
                    }
                    /** ======================================================== */
                }
            })
            childFragmentManager.let { it1 ->
                searchDialogFragment.show(
                    it1,
                    BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val dataModel = appState.data
                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {

                        binding.mainActivityRecyclerview.adapter =
                            MainAdapter(onListItemClickListener, dataModel, GlideImageLoader())
                    } else {
                        binding.mainActivityRecyclerview.let { adapter!!.setData(dataModel) }
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            model.getData("Welcome!", true)
            // RX .observe(viewLifecycleOwner, observer)

        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }

    /** Реализация Koin ========================== */
    private fun initViews() {
        binding.mainActivityRecyclerview.layoutManager =
            LinearLayoutManager(context)
        binding.mainActivityRecyclerview.adapter = adapter
    }

    private fun iniViewModel() {
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, observer)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter?.setData(data)
    }
    /** ============================================= */

}