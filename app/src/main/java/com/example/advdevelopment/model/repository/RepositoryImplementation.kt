package com.example.advdevelopment.model.repository

import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.datasource.DataSource
import com.example.advdevelopment.model.repository.Repository

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
