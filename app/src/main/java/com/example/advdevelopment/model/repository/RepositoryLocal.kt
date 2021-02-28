package com.example.advdevelopment.model.repository

import com.example.advdevelopment.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
