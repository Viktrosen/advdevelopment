package advdevelopment.com.translator.view.main

import advdevelopment.com.core.viewmodel.Interactor
import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.dto.SearchResultDto
import advdevelopment.com.repository.Repository
import advdevelopment.com.repository.RepositoryLocal
import advdevelopment.com.translator.utils.mapSearchResultToResult

class MainInteractor(
        private val repositoryRemote: Repository<List<SearchResultDto>>,
        private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {
    
    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(mapSearchResultToResult(repositoryRemote.getData(word)))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(mapSearchResultToResult(repositoryLocal.getData(word)))
        }
        return appState
    }
}

