package org.example.project


import android.app.Application
import org.example.project.core.di.appModules
import org.example.project.core.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            // Usamos tu lista de módulos
            modules(platformModule + appModules())
        }
    }
}