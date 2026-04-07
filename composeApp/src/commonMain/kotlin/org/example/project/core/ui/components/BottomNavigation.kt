package org.example.project.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.core.datastore.TokenManager
import org.example.project.core.navigation.CartScreenItem
import org.example.project.core.navigation.LoginScreenItem
import org.example.project.core.navigation.ProductsScreenItem
import org.example.project.core.navigation.ProfileScreenItem
import org.koin.compose.koinInject


@Composable
fun BottomNavigation(
    navigator: Navigator,
    tokenManager: TokenManager = koinInject()
) {
    // 1. Obtenemos la pantalla actual directamente del navigator
    val currentScreen = navigator.lastItem

    // Recolectamos el token. Si es null, el usuario no está logueado.
    val token by tokenManager.token.collectAsState(initial = null)
    val isLoggedIn = !token.isNullOrBlank()

    Box(
        modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // --- LA BARRA DE NAVEGACIÓN ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 16.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // 1. INICIO
                BottomNavItem(
                    label = "Inicio",
                    icon = Icons.Default.Home,
                    // Comparamos si la pantalla actual es la de Productos
                    selected = currentScreen is ProductsScreenItem,
                    onClick = {
                        // Si no estamos en productos, vamos allá limpiando la pila
                        if (currentScreen !is ProductsScreenItem) {
                            navigator.replaceAll(ProductsScreenItem)
                        }
                    }
                )

                Spacer(modifier = Modifier.width(60.dp))

                // 2. MI CUENTA
                BottomNavItem(
                    label = "Cuenta",
                    icon = Icons.Default.Person,
                    // Ahora seleccionamos si estamos en Login O en Profile
                    selected = currentScreen is LoginScreenItem || currentScreen is ProfileScreenItem,
                    onClick = {
                        if (isLoggedIn) {
                            // SI ESTÁ LOGUEADO: Va directo al Perfil
                            if (currentScreen !is ProfileScreenItem) {
                                navigator.push(ProfileScreenItem)
                            }
                        } else {
                            // SI NO ESTÁ LOGUEADO: Va al Login
                            if (currentScreen !is LoginScreenItem) {
                                navigator.push(LoginScreenItem)
                            }
                        }
                    }
                )
            }
        }

        // --- EL CARRITO RESALTADO (FAB) ---
        FloatingActionButton(
            onClick = {
                navigator.push(CartScreenItem)
            },
            shape = CircleShape,
            containerColor = Color.White,
            contentColor = Color.Black,
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            modifier = Modifier
                .offset(y = (-25).dp)
                .size(65.dp)
                .border(1.dp, Color.LightGray, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Carrito",
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = "Carrito",
            fontSize = 11.sp,
            modifier = Modifier.offset(y = (-5).dp),
            color = Color.Gray
        )
    }
}

@Composable
fun BottomNavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
            modifier = Modifier.size(26.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}