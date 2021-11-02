package q4.test_coverage.mytests.repository


import q4.test_coverage.mytests.model.SearchResponse
import q4.test_coverage.mytests.presenter.RepositoryContract
import retrofit2.Response

internal class GitHubRepository(private val gitHubApi: GitHubApi) : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}