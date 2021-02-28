package com.example.advdevelopment.model.datasource

import com.example.advdevelopment.model.data.AppState
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.room.HistoryDao
import com.example.advdevelopment.utils.convertDataModelSuccessToEntity
import com.example.advdevelopment.utils.mapHistoryEntityToSearchResult

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
