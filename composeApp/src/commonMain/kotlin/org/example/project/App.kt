package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.example.project.core.navigation.NavigationGraph
import org.example.project.core.theme.WoodcraftTheme

import org.example.project.feature.auth.presentation.LoginScreen
import org.example.project.feature.auth.presentation.LoginViewModel
import org.example.project.feature.products.presentation.ProductsScreen
import org.example.project.feature.products.presentation.ProductsViewModel
import org.example.project.feature.register.presentation.RegisterScreen
import org.example.project.feature.register.presentation.RegisterViewModel
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    WoodcraftTheme {
        // 2. Llamamos a nuestro Grafo de navegación
        Surface {
            NavigationGraph(navController = navController)
        }
    }
}