package q4.test_coverage.mytests.presenter.details

import android.util.Log
import q4.test_coverage.mytests.view.ViewContract
import q4.test_coverage.mytests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private var count: Int = 0
) : PresenterDetailsContract {

    private val tag = "myLogs"

    private var viewContract: ViewDetailsContract? = null

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract?.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract?.setCount(count)
    }

    override fun onAttach(viewContract: ViewContract) {
        if (viewContract != this.viewContract) {
            this.viewContract = viewContract as ViewDetailsContract
            Log.d(tag, "$viewContract -> View прицепилась <<<<<<<<<<<<<<<<<<<<")
        } else
            Log.d(tag, "View прицепилась как новая <<<<<<<<<<<<<<")
    }

    override fun onDetach(viewContract: ViewContract) {
        if (viewContract as ViewDetailsContract == this.viewContract) {
            this.viewContract = null
            Log.d(tag, "$viewContract -> View обнулилась <<<<<<<<<<<<<<<<<<<<")
        } else
            Log.d(tag, "$viewContract -> View НЕ обнулилась <<<<<<<<<<<<<<<<<<<<")
    }
}
