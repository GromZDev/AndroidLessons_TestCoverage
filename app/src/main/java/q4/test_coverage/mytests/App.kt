package q4.test_coverage.mytests

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import q4.test_coverage.mytests.di.koin.application
import q4.test_coverage.mytests.di.koin.historyScreen
import q4.test_coverage.mytests.di.koin.mainScreen

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}