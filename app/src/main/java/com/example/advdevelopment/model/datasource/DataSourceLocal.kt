package com.example.advdevelopment.model.datasource

import com.example.advdevelopment.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}
