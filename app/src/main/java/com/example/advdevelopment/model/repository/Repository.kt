package com.example.advdevelopment.model.repository

interface Repository<T> {

    suspend fun getData(word: String): T
}
