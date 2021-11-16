package q4.test_coverage.mytests.interactor

import q4.test_coverage.mytests.model.AppState
import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.model.datasource.roomlocal.RepositoryLocal
import q4.test_coverage.mytests.repository.Repository

class HistoryInterActor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : LogicInterActor<AppState> {
    override suspend fun getData(word: String, fromRemoteSource: Boolean):
            AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
