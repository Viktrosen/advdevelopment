package com.example.advdevelopment.presenter

import com.example.advdevelopment.model.data.TranslatorData
import com.example.advdevelopment.view.base.BaseActivity
import com.example.advdevelopment.view.base.View
import com.example.advdevelopment.view.main.MainActivity
import junit.framework.TestCase
import org.junit.Before

class MainPresenterImplTest : TestCase() {

     var presenter: Presenter<TranslatorData, View> = MainPresenterImpl()
     var f =  presenter::class.java.getDeclaredField("currentView")

    fun testAttachView() {
        f.isAccessible = true

        presenter.attachView(MainActivity())
        assertNotNull(f.get(presenter))
    }

    fun testDetachView() {
        f.isAccessible = true
        presenter.detachView(MainActivity())
        assertNull(f.get(presenter))
    }
}