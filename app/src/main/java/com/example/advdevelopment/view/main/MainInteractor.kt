package com.example.advdevelopment.view.main

import com.example.advdevelopment.model.data.AppState
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.repository.Repository
import com.example.advdevelopment.viewmodel.Interactor

class MainInteractor(
        private val repositoryRemote: Repository<List<DataModel>>,
        private val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
