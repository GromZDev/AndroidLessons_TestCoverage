package q4.test_coverage.mytests.presenter.details

import q4.test_coverage.mytests.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}
