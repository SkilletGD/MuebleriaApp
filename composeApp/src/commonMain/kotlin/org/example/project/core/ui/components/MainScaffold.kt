package org.example.project.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// ELIMINA las importaciones de androidx.navigation (navController, backStack, etc.)
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.project.core.navigation.LoginScreenItem
import org.example.project.core.navigation.ProductDetailScreenItem
import org.example.project.core.navigation.ProductsScreenItem
import org.example.project.core.navigation.RegisterScreenItem

@Composable
fun MainScaffold() {
    // Voyager maneja su propio estado, no necesitas rememberNavController()
    Navigator(screen = ProductsScreenItem) { navigator ->

        // Accedemos a la pantalla actual de forma directa y reactiva
        val currentScreen = navigator.lastItem

        // Lógica de visibilidad (Type-safe)
        val showBars = when (currentScreen) {
            is LoginScreenItem,
            is RegisterScreenItem,
            is ProductDetailScreenItem -> false
            else -> true
        }

        Scaffold(
            topBar = {
                if (showBars) {
                    CustomTopBar(
                        currentScreen = navigator.lastItem,
                        onBack = {
                            // Si puede regresar, que lo haga, si no, que vaya al inicio
                            if (navigator.canPop) navigator.pop()
                            else navigator.replaceAll(ProductsScreenItem)
                        }
                    )
                }
            },
            bottomBar = {
                if (showBars) {
                    // Asegúrate de que tu componente BottomNavigation
                    // ahora acepte un "Navigator" de Voyager en lugar de NavHostController
                    BottomNavigation(navigator)
                }
            }
        ) { innerPadding ->
            // El padding del Scaffold es importante para que el contenido
            // no se meta debajo de las barras
            Box(modifier = Modifier.padding(innerPadding)) {
                SlideTransition(navigator)
            }
        }
    }
}