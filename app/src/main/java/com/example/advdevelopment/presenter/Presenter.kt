package com.example.advdevelopment.presenter

import com.example.advdevelopment.model.data.TranslatorData
import com.example.advdevelopment.view.base.View

interface Presenter<T : TranslatorData, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
