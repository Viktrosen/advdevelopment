package com.example.advdevelopment.presenter

import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.data.TranslatorData
import com.example.advdevelopment.model.datasource.DataSourceLocal
import com.example.advdevelopment.model.datasource.DataSourceRemote
import com.example.advdevelopment.model.repository.Repository
import com.example.advdevelopment.model.repository.RepositoryImplementation
import io.reactivex.Observable
import junit.framework.TestCase
import org.junit.Before

class MainInteractorTest() : TestCase() {

    private var interactor:Interactor<TranslatorData> = MainInteractor(RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal()))


    fun testGetData() {
        assertNotNull(interactor.getData("someString",true))
    }
}