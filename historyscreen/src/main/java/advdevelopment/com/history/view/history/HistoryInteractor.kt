package advdevelopment.com.history.view.history

import advdevelopment.com.core.viewmodel.Interactor
import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.DataModel
import advdevelopment.com.repository.Repository
import advdevelopment.com.repository.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
