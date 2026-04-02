package org.example.project.feature.productdetail.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
// 1. IMPORTA TU TEMA CENTRALIZADO (Ajusta el paquete si es necesario)
import org.example.project.core.theme.*

// 2. IMPORTA LAS LIBRERÍAS DE MATERIAL3 QUE FALTAN
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.AssistChip // Por si acaso
import androidx.compose.material3.Surface



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductDetailViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var selectedVariantIndex by remember { mutableStateOf(0) }
    var quantity by remember { mutableStateOf(1) }

    // Cargar datos al iniciar
    LaunchedEffect(productId) {
        viewModel.onEvent(ProductDetailEvent.LoadProduct(productId))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodGradientBackground)
    ) {
        // Estado de carga
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = WoodPrimary,
                        strokeWidth = 4.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Cargando detalles...",
                        color = WoodTextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        }
        // Estado de error
        else if (state.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WoodSurface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            tint = WoodError,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Error al cargar el producto",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = WoodTextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.error ?: "Error desconocido",
                            color = WoodError,
                            fontSize = 14.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { viewModel.onEvent(ProductDetailEvent.LoadProduct(productId)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WoodPrimary)
                        ) {
                            Text("Reintentar", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = onBack
                        ) {
                            Text("Volver", color = WoodTextSecondary)
                        }
                    }
                }
            }
        }
        // Estado con producto cargado
        else {
            state.product?.let { product ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header con imagen y botón de regreso
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    ) {
                        // Imagen principal
                        AsyncImage(
                            model = product.imagenUrl ?: "https://nikkoauto.mx/img/sin-foto.png",
                            contentDescription = product.nombre,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                            contentScale = ContentScale.Crop
                        )

                        // Gradiente superior para el botón de regreso
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.5f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )

                        // Botón de regreso
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.5f),
                                    RoundedCornerShape(50.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Regresar",
                                tint = Color.White
                            )
                        }

                        // Badge de categoría
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = WoodPrimary.copy(alpha = 0.9f)
                        ) {
                            Text(
                                text = product.categoriaNombre,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Contenido del producto
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .shadow(4.dp, RoundedCornerShape(24.dp), clip = true),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = WoodSurface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            // Nombre y precio
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = product.nombre,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = WoodTextPrimary,
                                        fontSize = 24.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Código: ${product.id}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = WoodTextHint,
                                        fontSize = 12.sp
                                    )
                                }

                                Text(
                                    text = "$${product.precioBase}",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = WoodPrimary,
                                    fontSize = 28.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Descripción
                            Divider(
                                color = WoodTextHint.copy(alpha = 0.3f),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = "Descripción",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = WoodTextPrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = product.descripcion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = WoodTextSecondary,
                                lineHeight = 22.sp
                            )

                            // Variantes disponibles
                            if (product.variantes.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(24.dp))
                                Divider(
                                    color = WoodTextHint.copy(alpha = 0.3f),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                Text(
                                    text = "Opciones disponibles",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = WoodTextPrimary
                                )
                                Spacer(modifier = Modifier.height(12.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(product.variantes.size) { index ->
                                        val variante = product.variantes[index]
                                        FilterChip(
                                            selected = selectedVariantIndex == index,
                                            onClick = { selectedVariantIndex = index },
                                            label = {
                                                Column {
                                                    Text(
                                                        text = variante.color,
                                                        fontWeight = if (selectedVariantIndex == index) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                    Text(
                                                        text = "${variante.stock} disponibles",
                                                        fontSize = 10.sp,
                                                        color = WoodTextHint
                                                    )
                                                }
                                            },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = WoodPrimary.copy(alpha = 0.1f),
                                                selectedLabelColor = WoodPrimary
                                            )
                                        )
                                    }
                                }
                            }

                            // Selector de cantidad
                            Spacer(modifier = Modifier.height(24.dp))
                            Divider(
                                color = WoodTextHint.copy(alpha = 0.3f),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Cantidad",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = WoodTextPrimary
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    IconButton(
                                        onClick = { if (quantity > 1) quantity-- },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(
                                                WoodPrimary.copy(alpha = 0.1f),
                                                RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Disminuir",
                                            tint = WoodPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Text(
                                        text = quantity.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = WoodTextPrimary
                                    )

                                    IconButton(
                                        onClick = { quantity++ },
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(
                                                WoodPrimary.copy(alpha = 0.1f),
                                                RoundedCornerShape(8.dp)
                                            )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Aumentar",
                                            tint = WoodPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                            // Botón de acción principal
                            Spacer(modifier = Modifier.height(32.dp))

                            Button(
                                onClick = { /* Lógica para agregar al carrito */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = WoodPrimary
                                )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddShoppingCart,
                                        contentDescription = "Agregar al carrito",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Agregar al carrito - $${(product.precioBase.toDoubleOrNull() ?: 0.0) * quantity}",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Botón secundario
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedButton(
                                onClick = { /* Lógica para comprar ahora */ },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = WoodPrimary
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(WoodPrimary, WoodSecondary)
                                    )
                                )
                            ) {
                                Text(
                                    text = "Comprar ahora",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}