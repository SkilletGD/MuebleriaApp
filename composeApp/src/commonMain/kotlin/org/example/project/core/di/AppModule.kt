package org.example.project.core.di


import org.example.project.feature.auth.di.authModule
import org.example.project.feature.products.di.productsModule
import org.example.project.feature.register.di.registerModule

// Cuando crees más features (productos, carrito), los importarás aquí

fun appModules() = listOf(
    coreModule,   // El que tiene el HttpClient y TokenManager
    authModule,    // El que tiene la API, Repositorio y ViewModel de Auth
    registerModule,
    productsModule
)