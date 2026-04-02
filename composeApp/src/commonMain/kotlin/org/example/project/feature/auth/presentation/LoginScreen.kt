package org.example.project.feature.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// --- IMPORTS DEL TEMA ---
import org.example.project.core.theme.*
import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.auth.data.model.AuthResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // --- ESTADO PARA EL DIÁLOGO ---
    var showNotAvailableDialog by remember { mutableStateOf(false) }

    val state by viewModel.loginState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val gradientBrush = Brush.verticalGradient(colors = listOf(WoodPrimary, WoodSecondary))
    val backgroundGradient = Brush.verticalGradient(colors = listOf(WoodBackground, WoodSurface))

    // Usamos Box como contenedor raíz ahora que no hay Scaffold
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 40.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header con logo y título
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .shadow(8.dp, RoundedCornerShape(45.dp))
                        .clip(RoundedCornerShape(45.dp))
                        .background(gradientBrush),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(45.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Mueblería Central",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = WoodPrimary
                )

                Text(
                    text = "Tu hogar, nuestro estilo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WoodTextSecondary
                )
            }

            // Formulario
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = WoodSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, null, tint = WoodPrimary) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WoodPrimary,
                            focusedLabelColor = WoodPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = WoodPrimary) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = WoodPrimary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WoodPrimary,
                            focusedLabelColor = WoodPrimary
                        ),
                        singleLine = true
                    )

                    // --- BOTÓN OLVIDASTE CONTRASEÑA ---
                    TextButton(
                        onClick = { showNotAvailableDialog = true },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("¿Olvidaste tu contraseña?", color = WoodPrimary, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { if (email.isNotBlank() && password.isNotBlank()) viewModel.login(email, password) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WoodPrimary),
                        enabled = state !is NetworkResult.Loading
                    ) {
                        if (state is NetworkResult.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Ingresar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }

            // Footer
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿No tienes una cuenta? ", color = WoodTextSecondary)
                TextButton(onClick = { onNavigateToRegister() }) {
                    Text("Regístrate", color = WoodPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }

        // --- COMPONENTES FLOTANTES (Snackbar y Diálogo) ---

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        if (showNotAvailableDialog) {
            AlertDialog(
                onDismissRequest = { showNotAvailableDialog = false },
                confirmButton = {
                    TextButton(onClick = { showNotAvailableDialog = false }) {
                        Text("Entendido", color = WoodPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                title = {
                    Text("Función en desarrollo", color = WoodTextPrimary)
                },
                text = {
                    Text("Estamos trabajando para que pronto puedas recuperar tu contraseña. Por ahora, contacta al administrador.", color = WoodTextSecondary)
                },
                containerColor = WoodSurface,
                shape = RoundedCornerShape(20.dp)
            )
        }
    }

    // Efectos de red
    LaunchedEffect(state) {
        when (state) {
            is NetworkResult.Success<*> -> onLoginSuccess()
            is NetworkResult.Error -> {
                snackbarHostState.showSnackbar(
                    message = (state as NetworkResult.Error).message ?: "Error de conexión",
                    withDismissAction = true
                )
            }
            else -> {}
        }
    }
}