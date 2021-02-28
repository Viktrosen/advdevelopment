package com.example.advdevelopment.application

import android.app.Application
import com.example.advdevelopment.di.application
import com.example.advdevelopment.di.historyScreen
import com.example.advdevelopment.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}
