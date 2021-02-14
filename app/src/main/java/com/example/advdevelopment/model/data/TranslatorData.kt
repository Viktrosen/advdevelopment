package com.example.advdevelopment.model.data

sealed class TranslatorData {

    data class Success(val data: List<DataModel>?) : TranslatorData()
    data class Error(val error: Throwable) : TranslatorData()
    data class Loading(val progress: Int?) : TranslatorData()
}
