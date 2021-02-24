package com.example.advdevelopment.model.datasource

import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.datasource.DataSource

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
