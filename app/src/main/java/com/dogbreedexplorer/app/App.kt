package com.dogbreedexplorer.app

import android.app.Application
import com.dogbreedexplorer.module.dbModule
import com.dogbreedexplorer.module.networkModule
import com.dogbreedexplorer.module.repositoryModule
import com.dogbreedexplorer.module.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    viewModule,
                    repositoryModule,
                    networkModule,
                    dbModule(this@App)
                )
            )
        }
    }
}