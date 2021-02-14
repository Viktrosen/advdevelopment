package com.example.advdevelopment.presenter


import com.example.advdevelopment.model.data.TranslatorData
import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.repository.Repository
import com.example.advdevelopment.presenter.Interactor
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<TranslatorData> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<TranslatorData> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { TranslatorData.Success(it) }
        } else {
            localRepository.getData(word).map { TranslatorData.Success(it) }
        }
    }
}
