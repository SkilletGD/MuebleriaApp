package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

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
    MaterialTheme {
       // val loginViewModel: LoginViewModel = koinInject()
        //val registerViewModel: RegisterViewModel = koinInject()

        //LoginScreen(viewModel = loginViewModel)

        //RegisterScreen(viewModel = registerViewModel)

        val productsViewModel: ProductsViewModel = koinInject()
        ProductsScreen(viewModel = productsViewModel)


    }
}