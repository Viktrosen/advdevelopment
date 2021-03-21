package advdevelopment.com.repository

import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.dto.SearchResultDto
import advdevelopment.com.repository.RepositoryLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResultDto>>) :
        RepositoryLocal<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
