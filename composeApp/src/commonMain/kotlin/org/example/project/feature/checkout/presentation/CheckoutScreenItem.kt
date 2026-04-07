package org.example.project.feature.checkout.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

// Este es el objeto que faltaba
object CheckoutScreenItem : Screen {

    @Composable
    override fun Content() {
        // Por ahora una pantalla simple para que no de error
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Pantalla de Finalizar Compra (Próximamente)")
        }
    }
}