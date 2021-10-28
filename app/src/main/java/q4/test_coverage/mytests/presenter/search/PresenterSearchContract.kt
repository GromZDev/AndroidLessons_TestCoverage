package q4.test_coverage.mytests.presenter.search

import q4.test_coverage.mytests.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
    //onAttach
    //onDetach
}
