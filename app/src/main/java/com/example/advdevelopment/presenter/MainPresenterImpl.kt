package com.example.advdevelopment.presenter

import com.example.advdevelopment.model.data.TranslatorData
import com.example.advdevelopment.model.datasource.DataSourceLocal
import com.example.advdevelopment.model.datasource.DataSourceRemote
import com.example.advdevelopment.model.repository.RepositoryImplementation
import com.example.advdevelopment.rx.SchedulerProvider
import com.example.advdevelopment.view.base.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainPresenterImpl<T : TranslatorData, V : View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(TranslatorData.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }



    private fun getObserver(): DisposableObserver<TranslatorData> {
        return object : DisposableObserver<TranslatorData>() {

            override fun onNext(translatorData: TranslatorData) {
                currentView?.renderData(translatorData)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(TranslatorData.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}
