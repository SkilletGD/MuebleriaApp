package org.example.project.feature.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.core.network.ApiEndpoints.Carrito.item
import org.example.project.core.theme.WoodError
import org.example.project.core.theme.WoodGradientBackground
import org.example.project.core.theme.WoodPrimary
import org.example.project.core.theme.WoodSecondary
import org.example.project.core.theme.WoodSurface
import org.example.project.core.theme.WoodTextHint
import org.example.project.core.theme.WoodTextPrimary
import org.example.project.core.theme.WoodTextSecondary
import org.example.project.feature.profile.domain.models.UserProfile
import org.example.project.feature.profile.presentation.utils.formatFecha

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(WoodGradientBackground)) {
        when (val s = state) {
            is ProfileUiState.Loading -> CircularProgressIndicator(
                Modifier.align(Alignment.Center),
                color = WoodPrimary
            )
            is ProfileUiState.Error -> Text(
                s.message,
                color = WoodError,
                modifier = Modifier.align(Alignment.Center)
            )
            is ProfileUiState.Success -> {
                // CAMBIO CLAVE: Usamos LazyColumn en lugar de Column + verticalScroll
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    // Eliminamos el padding vertical del modificador para que el Header toque arriba
                ) {
                    // --- 1. HEADER (Sección independiente) ---
                    item {
                        ProfileHeader(s.profile)
                    }

                    // --- 2. CONTENIDO CON PADDING ---
                    item {
                        Column(modifier = Modifier.padding(20.dp)) {
                            // TARJETAS DE ESTADÍSTICAS
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                //Para pruebas de compose es estatico
                                StatCard("Pedidos", "12", Icons.Default.ShoppingBag, Modifier.weight(1f))
                                StatCard("Favoritos", "5", Icons.Default.Favorite, Modifier.weight(1f))
                            }

                            Spacer(Modifier.height(24.dp))

                            // SECCIÓN DE INFORMACIÓN
                            Text("Información Personal", fontWeight = FontWeight.Bold, color = WoodTextPrimary)
                            Spacer(Modifier.height(12.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = WoodSurface),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    InfoRow(Icons.Default.Email, "Email", s.profile.email)
                                    Divider(Modifier.padding(vertical = 12.dp))
                                    InfoRow(Icons.Default.Phone, "Teléfono", s.profile.telefono)
                                    Divider(Modifier.padding(vertical = 12.dp))
                                    InfoRow(
                                        icon = Icons.Default.CalendarToday,
                                        label = "Miembro desde",
                                        value = formatFecha(s.profile.fechaRegistro)
                                    )
                                }
                            }

                            Spacer(Modifier.height(24.dp))
                            Text("Configuración", fontWeight = FontWeight.Bold, color = WoodTextPrimary)
                            Spacer(Modifier.height(12.dp))
                        }
                    }

                    // --- 3. MENÚ DE OPCIONES (Podrían ser items individuales si la lista crece) ---
                    item {
                        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                            MenuOption(Icons.Default.LocationOn, "Mis Direcciones")
                            MenuOption(Icons.Default.Payment, "Métodos de Pago")
                            MenuOption(Icons.Default.Notifications, "Notificaciones")
                        }
                    }

                    // --- 4. BOTÓN DE CIERRE DE SESIÓN ---
                    item {
                        PaddingValues(horizontal = 20.dp, vertical = 40.dp).let { p ->
                            Button(
                                onClick = {
                                    viewModel.logout()
                                    onLogout()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp) // Padding lateral del botón
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = WoodError.copy(alpha = 0.1f)),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, WoodError)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ExitToApp, null, tint = WoodError)
                                Spacer(Modifier.width(8.dp))
                                Text("Cerrar Sesión", color = WoodError, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.height(30.dp)) // Aire final para el scroll
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = WoodPrimary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(label, fontSize = 12.sp, color = WoodTextHint)
            Text(value, fontSize = 16.sp, color = WoodTextPrimary)
        }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = WoodSurface)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = WoodPrimary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(label, fontSize = 12.sp, color = WoodTextHint)
            }
        }
    }
}
@Composable
fun MenuOption(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { }.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = WoodTextSecondary, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(16.dp))
        Text(title, modifier = Modifier.weight(1f), color = WoodTextPrimary)
        Icon(Icons.Default.ChevronRight, null, tint = WoodTextHint)
    }
}

@Composable
fun ProfileHeader(profile: UserProfile) {
    Box(
        modifier = Modifier.fillMaxWidth().height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        // Fondo decorativo
        Box(modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(WoodPrimary, WoodSecondary))
        ))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier.size(90.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(profile.nombre.take(1), fontSize = 36.sp, fontWeight = FontWeight.Bold, color = WoodPrimary)
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(profile.nombre, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(profile.rol.name, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}