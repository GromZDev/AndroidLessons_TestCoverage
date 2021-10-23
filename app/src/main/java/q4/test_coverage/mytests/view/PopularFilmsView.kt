package q4.test_coverage.mytests.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PopularFilmsView : MvpView {
    fun init()
    fun updateList()
}
