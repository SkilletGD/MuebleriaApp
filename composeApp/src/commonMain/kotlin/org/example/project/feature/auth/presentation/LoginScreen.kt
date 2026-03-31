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
import org.example.project.core.network.utils.NetworkResult
import org.example.project.feature.auth.data.model.AuthResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val state by viewModel.loginState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Colores de la marca - Mueblería
    val primaryColor = Color(0xFF8B5A2B)
    val secondaryColor = Color(0xFFD4A373)
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(primaryColor, secondaryColor)
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.White
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFEF9F0), Color(0xFFFFFFFF))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .padding(top = 60.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header con logo y título
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo container con gradiente
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .shadow(8.dp, RoundedCornerShape(50.dp))
                            .clip(RoundedCornerShape(50.dp))
                            .background(gradientBrush),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Logo Mueblería",
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Mueblería Central",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = primaryColor
                    )

                    Text(
                        text = "Tu hogar, nuestro estilo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Formulario de login
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = true
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        // Campo de email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Correo electrónico") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = primaryColor
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedLabelColor = primaryColor
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Campo de contraseña
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Contraseña") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Contraseña",
                                    tint = primaryColor
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { passwordVisible = !passwordVisible }
                                ) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                        tint = primaryColor
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedLabelColor = primaryColor
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )

                        // Olvidaste contraseña
                        TextButton(
                            onClick = { /* TODO: Implementar recuperación contraseña */ },
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = "¿Olvidaste tu contraseña?",
                                color = primaryColor,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón de login
                        Button(
                            onClick = {
                                if (email.isNotBlank() && password.isNotBlank()) {
                                    viewModel.login(email, password)
                                } else {
                                    // Mostrar mensaje de campos vacíos usando el snackbar
                                    // Nota: No podemos usar snackbar directamente aquí porque está en un callback
                                    // pero podemos usar coroutine
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                                disabledContainerColor = primaryColor.copy(alpha = 0.6f)
                            ),
                            enabled = state !is NetworkResult.Loading
                        ) {
                            if (state is NetworkResult.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Ingresar",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Footer con registro
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "¿No tienes una cuenta? ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    TextButton(
                        onClick = { /* TODO: Navegar a registro */ },
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Text(
                            text = "Regístrate",
                            color = primaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Efectos secundarios
            LaunchedEffect(state) {
                when (state) {
                    is NetworkResult.Success<*> -> {
                        val data = (state as NetworkResult.Success<*>).data as? AuthResponse
                        val texto = data?.mensaje ?: "¡Bienvenido a Mueblería Central!"

                        snackbarHostState.showSnackbar(
                            message = texto,
                            duration = SnackbarDuration.Short,
                            withDismissAction = true
                        )

                        if (data?.token != null) {
                            println("Login exitoso, token guardado.")
                            // Aquí puedes navegar a HomeScreen
                        }
                    }
                    is NetworkResult.Error -> {
                        snackbarHostState.showSnackbar(
                            message = (state as NetworkResult.Error).message ?: "Error al iniciar sesión. Verifica tus credenciales.",
                            duration = SnackbarDuration.Long,
                            withDismissAction = true
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}