package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.example.project.core.di.appModules
import org.example.project.core.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // ⚠️ ESTO ES LO QUE EVITA QUE SE CIERRE LA APP
        startKoin {
            androidLogger() // Te ayuda a ver errores de Koin en el Logcat
            androidContext(this@MainActivity)
            modules(platformModule + appModules())
        }

        setContent {
            App()
        }
    }
}

