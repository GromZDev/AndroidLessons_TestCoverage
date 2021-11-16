package q4.test_coverage.mytests.repository

import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.model.datasource.retrofitremote.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    /** Coroutines -  */
    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}