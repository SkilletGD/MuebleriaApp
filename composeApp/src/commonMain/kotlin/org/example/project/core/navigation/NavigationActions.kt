package org.example.project.core.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class NavigationActions(private val navController: NavHostController) {

    fun navigateToDetail(id: Int) {
        navController.navigate(Screen.ProductDetail.createRoute(id)){
            // Evita que se abran múltiples copias de la misma pantalla
            // si se presiona el botón rápido varias veces
            launchSingleTop = true

            // Opcional: Restaura el estado si la pantalla ya existía
            restoreState = true
        }
    }

    fun navigateToLogin() {
        navController.navigate(Screen.Login.route){
            launchSingleTop = true
            popUpTo(Screen.Register.route) { inclusive = true }
        }
    }

    fun navigateToRegister() {
        navController.navigate(Screen.Register.route){
            // 1. Evita duplicados
            launchSingleTop = true
            // 2. Limpia el Login del historial al ir a Registro
            popUpTo(Screen.Login.route) { inclusive = true }
        }
    }
    fun navigateToHome() {
        navController.navigate(Screen.Products.route) {
            // Usamos el id del grafo de navegación de forma segura
            popUpTo(Screen.Products.route) {
                inclusive = true
            }
            // Evita que se abra doble si el usuario da click rápido
            launchSingleTop = true
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}