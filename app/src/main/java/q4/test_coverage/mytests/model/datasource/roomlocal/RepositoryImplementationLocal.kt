package q4.test_coverage.mytests.model.datasource.roomlocal

import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.model.DataModel

class RepositoryImplementationLocal(
    private val dataSource: DataSourceLocal<List<DataModel>>
) : RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)

    override suspend fun saveToDB(appState: AppState) = dataSource.saveToDB(appState)

}