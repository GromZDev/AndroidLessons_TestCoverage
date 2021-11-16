package q4.test_coverage.mytests.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import q4.test_coverage.mytests.App
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class
    ]
)

@Singleton
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(myApp: App)
}