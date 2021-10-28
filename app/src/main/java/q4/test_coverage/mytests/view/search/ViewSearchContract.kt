package q4.test_coverage.mytests.view.search

import q4.test_coverage.mytests.model.SearchResult
import q4.test_coverage.mytests.view.ViewContract

internal interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
