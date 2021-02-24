package com.example.advdevelopment.model.datasource

interface DataSource<T> {

    suspend fun getData(word: String): T
}
