package com.example.advdevelopment.model.repository

import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.datasource.DataSource
import com.example.advdevelopment.model.repository.Repository
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}
