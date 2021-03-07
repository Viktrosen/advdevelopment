package advdevelopment.com.repository

import advdevelopment.com.repository.DataSource
import advdevelopment.com.repository.Repository
import advdevelopment.com.model.data.DataModel

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
