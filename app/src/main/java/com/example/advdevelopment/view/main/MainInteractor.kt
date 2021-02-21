package com.example.advdevelopment.view.main

import com.example.advdevelopment.di.NAME_LOCAL
import com.example.advdevelopment.di.NAME_REMOTE
import com.example.advdevelopment.model.data.AppState
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.repository.Repository
import com.example.advdevelopment.viewmodel.Interactor
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote
        } else {
            repositoryLocal
        }.getData(word).map { AppState.Success(it) }
    }
}
