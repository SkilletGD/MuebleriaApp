package org.example.project.core.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.example.project.feature.auth.presentation.LoginScreen
import org.example.project.feature.auth.presentation.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel
// Importa tus pantallas (Features)
import org.example.project.feature.products.presentation.ProductsScreen
import org.example.project.feature.productdetail.presentation.ProductDetailScreen
import org.example.project.feature.register.presentation.RegisterScreen
import org.example.project.feature.register.presentation.RegisterViewModel



@Composable
fun NavigationGraph(navController: NavHostController) {
    // Creamos las acciones de navegación recordando el estado
    val actions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.Products.route // Mercado Libre Style: Inicia en productos
    ) {
        // --- CATÁLOGO DE PRODUCTOS ---
        composable(Screen.Products.route) {
            ProductsScreen(
                viewModel = koinViewModel(),
                onProductClick = { id -> actions.navigateToDetail(id) },
                onNavigateToLogin = { actions.navigateToLogin() }
            )
        }

        // --- DETALLE DEL PRODUCTO ---
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ProductDetailScreen(
                productId = id,
                viewModel = koinViewModel(),
                onBack = { actions.navigateBack() }
            )
        }

        // --- LOGIN ---
        composable(Screen.Login.route) {
            val loginViewModel = koinViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToRegister = { actions.navigateToRegister() },
                onLoginSuccess = {
                    // Acción cuando el login es exitoso
                    // Usamos popUpTo para limpiar el historial y que no pueda regresar al Login
                    navController.navigate(Screen.Products.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )

        }

        // --- REGISTRO ---
        composable(Screen.Register.route) {
            val registerViewModel = koinViewModel<RegisterViewModel>()
            RegisterScreen(
                viewModel = registerViewModel,
                onNavigateToLogin = { actions.navigateToLogin() },
                onNavigateToHome = {
                    navController.navigate(Screen.Products.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
    }
}