package org.example.project.feature.register.presentation

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import org.example.project.core.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: RegisterViewModel = koinInject()
) {
    val formState by viewModel.formState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // Estados para manejar visibilidad de contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    // Manejo de navegación tras registro exitoso
    LaunchedEffect(uiState) {
        if (uiState is RegistroUiState.Success) {
            onNavigateToHome()
        }
    }

    // Fondo con gradiente
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodGradientBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(top = 40.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header con logo
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo container
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .shadow(8.dp, RoundedCornerShape(40.dp))
                        .clip(RoundedCornerShape(40.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(WoodPrimary, WoodSecondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Registro",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Crear Cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = WoodPrimary
                )

                Text(
                    text = "Únete a nuestra comunidad",
                    style = MaterialTheme.typography.bodyMedium,
                    color = WoodTextSecondary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Formulario de registro
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = true
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = WoodSurface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Campo: Nombre completo
                    OutlinedTextField(
                        value = formState.nombre,
                        onValueChange = { viewModel.onEvent(RegistroEvent.NombreChanged(it)) },
                        label = { Text("Nombre completo") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Nombre",
                                tint = WoodPrimary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WoodPrimary,
                            unfocusedBorderColor = WoodTextHint.copy(alpha = 0.5f),
                            focusedLabelColor = WoodPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo: Email
                    OutlinedTextField(
                        value = formState.email,
                        onValueChange = { viewModel.onEvent(RegistroEvent.EmailChanged(it)) },
                        label = { Text("Correo electrónico") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = WoodPrimary
                            )
                        },
                        isError = formState.emailError != null,
                        supportingText = {
                            if (formState.emailError != null) {
                                Text(
                                    text = formState.emailError!!,
                                    color = WoodError,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WoodPrimary,
                            unfocusedBorderColor = WoodTextHint.copy(alpha = 0.5f),
                            focusedLabelColor = WoodPrimary,
                            errorBorderColor = WoodError,
                            errorLabelColor = WoodError
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    //Spacer(modifier = Modifier.height(16.dp))

                    // Campo: Teléfono
                    OutlinedTextField(
                        value = formState.telefono,
                        onValueChange = {
                            // Permitir solo números y limitar a 10 dígitos
                            val newValue = it.filter { char -> char.isDigit() }
                            if (newValue.length <= 10) {
                                viewModel.onEvent(RegistroEvent.TelefonoChanged(newValue))
                            }
                        },
                        label = { Text("Teléfono (10 dígitos)") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Teléfono",
                                tint = WoodPrimary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        // Asegurar que el teclado numérico aparezca
                        visualTransformation = VisualTransformation.None
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo: Contraseña
                    OutlinedTextField(
                        value = formState.password,
                        onValueChange = { viewModel.onEvent(RegistroEvent.PasswordChanged(it)) },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = WoodPrimary
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                    tint = WoodPrimary
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WoodPrimary,
                            unfocusedBorderColor = WoodTextHint.copy(alpha = 0.5f),
                            focusedLabelColor = WoodPrimary
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de registro
                    Button(
                        onClick = { viewModel.onEvent(RegistroEvent.OnRegisterClick) },
                        enabled = formState.isValid && uiState !is RegistroUiState.Loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WoodPrimary,
                            disabledContainerColor = WoodPrimary.copy(alpha = 0.6f)
                        )
                    ) {
                        if (uiState is RegistroUiState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Registrarse",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Error del servidor
                    if (uiState is RegistroUiState.Error) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = WoodError.copy(alpha = 0.1f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "Error",
                                    tint = WoodError,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = (uiState as RegistroUiState.Error).message,
                                    color = WoodError,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            // Footer con link a login
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "¿Ya tienes cuenta? ",
                    color = WoodTextSecondary,
                    fontSize = 14.sp
                )
                TextButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        text = "Inicia sesión",
                        color = WoodPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}