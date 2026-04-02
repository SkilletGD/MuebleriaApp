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
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.project.core.navigation.Screen

@Composable
fun BottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Contenedor principal para permitir que el carrito sobresalga
    Box(
        modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // --- LA BARRA DE NAVEGACIÓN ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp), // Altura estándar para e-commerce
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
                    selected = currentRoute == Screen.Products.route,
                    onClick = { /* Navegación a Products */ }
                )


                // ESPACIO PARA EL CARRITO (Hueco vacío en el Row)
                Spacer(modifier = Modifier.width(60.dp))

                // 3. MI CUENTA
                BottomNavItem(
                    label = "Cuenta",
                    icon = Icons.Default.Person,
                    selected = currentRoute == Screen.Login.route,
                    onClick = { navController.navigate(Screen.Login.route) }
                )

            }
        }

        // --- EL CARRITO RESALTADO (FAB) ---
        FloatingActionButton(
            onClick = { /* Navegar al Carrito */ },
            shape = CircleShape,
            containerColor = Color.White, // Fondo blanco como en tu imagen
            contentColor = Color.Black,
            elevation = elevation(8.dp),
            modifier = Modifier
                .offset(y = (-25).dp) // Esto lo sube para que resalte
                .size(65.dp)
                .border(1.dp, Color.LightGray, CircleShape) // Borde sutil
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Carrito",
                modifier = Modifier.size(30.dp)
            )
        }

        // Texto pequeño debajo del carrito (opcional para estilo exacto)
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