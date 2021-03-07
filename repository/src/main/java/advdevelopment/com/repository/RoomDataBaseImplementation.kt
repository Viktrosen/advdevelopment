package advdevelopment.com.repository

import advdevelopment.com.model.data.AppState
import advdevelopment.com.model.data.DataModel
import advdevelopment.com.repository.room.HistoryDao
import advdevelopment.com.repository.convertDataModelSuccessToEntity
import advdevelopment.com.repository.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
