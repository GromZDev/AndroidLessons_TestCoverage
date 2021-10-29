package q4.test_coverage.mytests.presenter

import q4.test_coverage.mytests.view.ViewContract

internal interface PresenterContract {

    fun onAttach(viewContract: ViewContract)

    fun onDetach(viewContract: ViewContract)
}
