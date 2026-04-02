package org.example.project.feature.productdetail.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage // O el que tengas configurado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // Cargar datos al iniciar
    LaunchedEffect(productId) {
        viewModel.onEvent(ProductDetailEvent.LoadProduct(productId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
            }
        } else {
            state.product?.let { product ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Imagen
                    AsyncImage(
                        model = product.imagenUrl ?: "https://nikkoauto.mx/img/sin-foto.png",
                        contentDescription = product.nombre,
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        // Información Básica
                        Text(
                            text = product.nombre,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$${product.precioBase}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Descripción
                        Text(
                            text = "Descripción",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = product.descripcion,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Variantes (Si existen)
                        if (product.variantes.isNotEmpty()) {
                            Text(
                                text = "Opciones disponibles:",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Row(modifier = Modifier.padding(top = 8.dp)) {
                                product.variantes.forEach { variante ->
                                    AssistChip(
                                        onClick = { },
                                        label = { Text("${variante.color} (${variante.stock} disp.)") },
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón de acción
                        Button(
                            onClick = { /* Lógica para agregar al carrito */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Añadir al carrito")
                        }
                    }
                }
            }
        }
    }
}