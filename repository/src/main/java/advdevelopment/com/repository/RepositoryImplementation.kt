package advdevelopment.com.repository

import advdevelopment.com.repository.DataSource
import advdevelopment.com.repository.Repository
import advdevelopment.com.model.data.dto.SearchResultDto

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResultDto>>) :
        Repository<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }
}
