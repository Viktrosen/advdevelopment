package com.example.advdevelopment.model.repository

import com.example.advdevelopment.model.data.AppState
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.datasource.DataSourceLocal
import com.example.advdevelopment.model.repository.RepositoryLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
