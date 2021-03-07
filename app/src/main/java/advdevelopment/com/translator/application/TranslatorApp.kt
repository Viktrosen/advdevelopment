package advdevelopment.com.translator.application

import android.app.Application
import advdevelopment.com.translator.di.application
import advdevelopment.com.translator.di.historyScreen
import advdevelopment.com.translator.di.mainScreen
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
