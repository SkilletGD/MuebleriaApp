package org.example.project.core.navigation


import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.feature.auth.presentation.LoginScreen
import org.example.project.feature.cart.presentation.CartEvent
import org.example.project.feature.cart.presentation.CartScreen
import org.example.project.feature.cart.presentation.CartViewModel
import org.example.project.feature.checkout.presentation.CheckoutScreenItem
import org.example.project.feature.productdetail.presentation.ProductDetailScreen
import org.example.project.feature.products.presentation.ProductsScreen
import org.example.project.feature.profile.presentation.ProfileScreen
import org.example.project.feature.register.presentation.RegisterScreen
import org.example.project.feature.search.presentation.SearchScreen
import org.koin.compose.viewmodel.koinViewModel

// --- DEFINICIÓN DE PANTALLAS ---

/**
 * Pantalla de Catálogo de Productos
 */
object ProductsScreenItem : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val cartViewModel: CartViewModel = koinViewModel()
        ProductsScreen(
            viewModel = koinViewModel(),
            onProductClick = { id ->
                // Equivale a navigateToDetail(id)
                navigator.push(ProductDetailScreenItem(productId = id))
            },
            onAddToCart = { productId ->
                // 2. Ahora 'cartViewModel' ya existe en este scope
                cartViewModel.onEvent(
                    CartEvent.OnAddToCart(variantId = productId, quantity = 1)
                )

                navigator.push(CartScreenItem)
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
        val cartViewModel: CartViewModel = koinViewModel()
        ProductDetailScreen(
            productId = productId,
            viewModel = koinViewModel(),
            onBack = {
                // Equivale a navigateBack()
                navigator.pop()
            },
            onAddToCart = { variantId, quantity ->
                cartViewModel.onEvent(
                    CartEvent.OnAddToCart(variantId = variantId, quantity = quantity)
                )

                navigator.push(CartScreenItem)
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

object ProfileScreenItem : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ProfileScreen(
            viewModel = koinViewModel(),
            onLogout = {
                navigator.replaceAll(LoginScreenItem)
            }
        )
    }
}

object SearchScreenItem : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // Llamamos a la "Screen" que está en el feature de search
        SearchScreen(
            viewModel = koinViewModel(),
            onProductClick = { productId ->
                // Navegamos al detalle cuando el usuario toque un resultado
                navigator.push(ProductDetailScreenItem(productId = productId))
            },
            onBack = {
                navigator.pop()
            }
        )
    }
}

object CartScreenItem : Screen{
    @Composable
    override fun Content(){
        val navigator = LocalNavigator.currentOrThrow

        CartScreen(
            viewModel = koinViewModel(),
            onNavigateToCheckout = {
                navigator.push(CheckoutScreenItem)
            }
        )
    }
}