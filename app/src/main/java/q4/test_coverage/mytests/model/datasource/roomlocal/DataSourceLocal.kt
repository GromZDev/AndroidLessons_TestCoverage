package q4.test_coverage.mytests.model.datasource.roomlocal

import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.model.datasource.retrofitremote.DataSource

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveToDB(appState: AppState)
}
