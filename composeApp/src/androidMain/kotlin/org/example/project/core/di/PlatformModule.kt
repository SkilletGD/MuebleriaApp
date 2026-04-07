package org.example.project.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.android.ext.koin.androidContext // <--- Este ya no debería salir en rojo
import org.koin.dsl.module

// Importante: El Context debe ser de android.content.Context
val android.content.Context.dataStore by preferencesDataStore(name = "muebleria_prefs")

val platformModule = module {
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }
}