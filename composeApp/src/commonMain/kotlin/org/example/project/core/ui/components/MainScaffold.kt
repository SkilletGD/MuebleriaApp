package org.example.project.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.project.core.navigation.NavigationGraph
import org.example.project.core.navigation.Screen

@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    // Obtenemos la ruta actual para saber qué mostrar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Lógica de visibilidad: Ocultar barras en Login, Registro y Detalle
    val showBars = when (currentRoute) {
        Screen.Login.route,
        Screen.Register.route,
        Screen.ProductDetail.route -> false
        else -> true
    }

    Scaffold(
        topBar = {
            if (showBars) {
                // Tu TopBar ahora "sentirá" el notch y bajará automáticamente
                CustomTopBar()
            }
        },
        bottomBar = {
            if (showBars) {
                BottomNavigation(navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // Llamamos a tu NavigationGraph pasando el controller
            NavigationGraph(navController = navController)
        }
    }
}