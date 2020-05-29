package com.pavel.a692group

import android.app.Application
import com.pavel.a692group.di.databaseModule
import com.pavel.a692group.di.preferenceModule
import com.pavel.a692group.di.repositoryModules
import com.pavel.a692group.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                viewModels +
                        repositoryModules +
                        databaseModule +
                        preferenceModule
                // networkModules
            )
        }
        instance = this
    }


    companion object {
        lateinit var instance: App
            private set
    }
}

