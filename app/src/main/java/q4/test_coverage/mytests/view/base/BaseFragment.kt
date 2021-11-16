package q4.test_coverage.mytests.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import q4.test_coverage.mytests.R
import q4.test_coverage.mytests.interactor.LogicInterActor
import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.utils.networkstatus.AlertDialogFragment
import q4.test_coverage.mytests.utils.networkstatus.isOnline
import q4.test_coverage.mytests.viewmodel.BaseViewModel

abstract class BaseFragment<T : AppState, I : LogicInterActor<T>> : Fragment() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)

    /** Проверка состояния сети ============================= */
    protected var isNetworkAvailable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isNetworkAvailable = context?.let { isOnline(it) } == true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = context?.let { isOnline(it) } == true
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun isDialogNull(): Boolean {
        return fragmentManager?.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        fragmentManager?.let {
            AlertDialogFragment.newInstance(title, message).show(it, DIALOG_FRAGMENT_TAG)
        }
    }

    abstract fun setDataToAdapter(data: List<DataModel>)

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "Network_detecting_TAG_999"
    }
    /** =========================================================== */
}