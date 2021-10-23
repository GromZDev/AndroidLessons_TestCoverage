package q4.test_coverage.mytests.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import q4.test_coverage.mytests.App
import q4.test_coverage.mytests.api.ApiHolder
import q4.test_coverage.mytests.databinding.FragmentPopularFilmsBinding
import q4.test_coverage.mytests.presenter.PopularFilmsPresenter
import q4.test_coverage.mytests.retrofit.GlideImageLoader
import q4.test_coverage.mytests.retrofit.RetrofitPopularFilmsRepo

class PopularFilmsFragment : MvpAppCompatFragment(), PopularFilmsView, BackButtonListener {

    companion object {
        fun newInstance() = PopularFilmsFragment()
    }

    private val presenter: PopularFilmsPresenter by moxyPresenter {
        PopularFilmsPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitPopularFilmsRepo(ApiHolder.api),
            App.instance.router
        )
    }

    private var adapter: PopularFilmsRVAdapter? = null
    private var vb: FragmentPopularFilmsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentPopularFilmsBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvUsers?.layoutManager = LinearLayoutManager(context)
        adapter = PopularFilmsRVAdapter(presenter.popularFilmsListPresenter, GlideImageLoader())
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}