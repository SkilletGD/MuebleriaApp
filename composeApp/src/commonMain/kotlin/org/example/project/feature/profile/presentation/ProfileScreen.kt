package org.example.project.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.core.theme.WoodError
import org.example.project.core.theme.WoodGradientBackground
import org.example.project.core.theme.WoodPrimary
import org.example.project.core.theme.WoodSecondary
import org.example.project.core.theme.WoodSurface
import org.example.project.core.theme.WoodTextHint
import org.example.project.core.theme.WoodTextPrimary

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(WoodGradientBackground)) {
        when (val s = state) {
            is ProfileUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center), color = WoodPrimary)
            is ProfileUiState.Error -> Text(s.message, color = WoodError, modifier = Modifier.align(Alignment.Center))
            is ProfileUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar e Info
                    Box(modifier = Modifier.size(100.dp).background(WoodPrimary, CircleShape), contentAlignment = Alignment.Center) {
                        Text(s.profile.nombre.take(1), color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.height(16.dp))
                    Text(s.profile.nombre, style = MaterialTheme.typography.headlineMedium, color = WoodTextPrimary)
                    Text(s.profile.rol.name, color = WoodSecondary, fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(32.dp))

                    // Card con detalles
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = WoodSurface),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            InfoRow(Icons.Default.Email, "Email", s.profile.email)
                            Divider(Modifier.padding(vertical = 8.dp))
                            InfoRow(Icons.Default.Phone, "Teléfono", s.profile.telefono)
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    Button(
                        onClick = {
                            viewModel.logout()
                            onLogout()
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WoodError),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
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