package com.example.advdevelopment.model.datasource

import com.example.advdevelopment.model.data.DataModel
import com.example.advdevelopment.model.datasource.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
