package advdevelopment.com.repository

import advdevelopment.com.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}
