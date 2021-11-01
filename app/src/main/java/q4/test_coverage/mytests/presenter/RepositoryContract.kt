package q4.test_coverage.mytests.presenter

import q4.test_coverage.mytests.repository.RepositoryCallback

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}
