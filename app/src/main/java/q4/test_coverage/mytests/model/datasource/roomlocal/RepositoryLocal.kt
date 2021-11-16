package q4.test_coverage.mytests.model.datasource.roomlocal

import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.repository.Repository

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}