package advdevelopment.com.history.view.history

import advdevelopment.com.core.viewmodel.Interactor
import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.dto.SearchResultDto
import advdevelopment.com.repository.Repository
import advdevelopment.com.repository.RepositoryLocal
import advdevelopment.com.translator.utils.mapSearchResultToResult

class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
                if (fromRemoteSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )
        )
    }
}
