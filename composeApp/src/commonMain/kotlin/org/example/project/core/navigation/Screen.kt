package org.example.project.core.navigation

sealed class Screen(val route: String) {
    object Products : Screen("products")
    object ProductDetail : Screen("product_detail/{id}") {
        fun createRoute(id: Int) = "product_detail/$id"
    }
    object Login : Screen("login")
    object Register : Screen("register")
}