package q4.test_coverage.mytests.presenter.search

import android.util.Log
import q4.test_coverage.mytests.model.SearchResponse
import q4.test_coverage.mytests.repository.GitHubRepository
import q4.test_coverage.mytests.repository.GitHubRepository.GitHubRepositoryCallback
import q4.test_coverage.mytests.view.ViewContract
import q4.test_coverage.mytests.view.search.ViewSearchContract
import retrofit2.Response

/**
 * В архитектуре MVP все запросы на получение данных адресуются в Репозиторий.
 * Запросы могут проходить через InterActor или UseCase, использовать источники
 * данных (DataSource), но суть от этого не меняется.
 * Непосредственно Презентер отвечает за управление потоками запросов и ответов,
 * выступая в роли регулировщика движения на перекрестке.
 */

internal class SearchPresenter internal constructor(
    private val repository: GitHubRepository
) : PresenterSearchContract, GitHubRepositoryCallback {

    private val tag = "myLogs"

    private var viewContract: ViewSearchContract? = null

    override fun searchGitHub(searchQuery: String) {
        viewContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(viewContract: ViewContract) {
        if (viewContract != this.viewContract) {
            this.viewContract = viewContract as ViewSearchContract
            Log.d(tag, "$viewContract -> View прицепилась <<<<<<<<<<<<<<<<<<<<")
        } else
            Log.d(tag, "View прицепилась как новая <<<<<<<<<<<<<<")
    }

    override fun onDetach(viewContract: ViewContract) {
        if (viewContract as ViewSearchContract == this.viewContract) {
            this.viewContract = null
            Log.d(tag, "$viewContract -> View обнулилась <<<<<<<<<<<<<<<<<<<<")
        } else
            Log.d(tag, "$viewContract -> View НЕ обнулилась <<<<<<<<<<<<<<<<<<<<")
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError("Search results or total count are null")
            }
        } else {
            viewContract?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }
}
