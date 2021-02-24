package com.example.advdevelopment.application

import android.app.Application
import com.example.advdevelopment.di.application
import com.example.advdevelopment.di.mainScreen
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainScreen))
        }
    }
}
