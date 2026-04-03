package org.example.project.core.navigation


import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.feature.auth.presentation.LoginScreen
import org.example.project.feature.productdetail.presentation.ProductDetailScreen
import org.example.project.feature.products.presentation.ProductsScreen
import org.example.project.feature.register.presentation.RegisterScreen
import org.koin.compose.viewmodel.koinViewModel

// --- DEFINICIÓN DE PANTALLAS ---

/**
 * Pantalla de Catálogo de Productos
 */
object ProductsScreenItem : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ProductsScreen(
            viewModel = koinViewModel(),
            onProductClick = { id ->
                // Equivale a navigateToDetail(id)
                navigator.push(ProductDetailScreenItem(productId = id))
            },
            onNavigateToLogin = {
                // Equivale a navigateToLogin()
                navigator.push(LoginScreenItem)
            }
        )
    }
}

/**
 * Pantalla de Detalle (Data class permite pasar parámetros de forma segura)
 */
data class ProductDetailScreenItem(val productId: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ProductDetailScreen(
            productId = productId,
            viewModel = koinViewModel(),
            onBack = {
                // Equivale a navigateBack()
                navigator.pop()
            }
        )
    }
}

/**
 * Pantalla de Login
 */
object LoginScreenItem : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        LoginScreen(
            viewModel = koinViewModel(),
            onNavigateToRegister = {
                // Reemplaza Login por Register en el historial
                navigator.replace(RegisterScreenItem)
            },
            onLoginSuccess = {
                // Limpia todo y va a Home (Equivale a popUpTo(inclusive))
                navigator.replaceAll(ProductsScreenItem)
            }
        )
    }
}

/**
 * Pantalla de Registro
 */
object RegisterScreenItem : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        RegisterScreen(
            viewModel = koinViewModel(),
            onNavigateToLogin = {
                navigator.replace(LoginScreenItem)
            },
            onNavigateToHome = {
                navigator.replaceAll(ProductsScreenItem)
            }
        )
    }
}