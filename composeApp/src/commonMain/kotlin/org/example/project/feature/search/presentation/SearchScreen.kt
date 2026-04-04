package org.example.project.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.core.theme.*
import org.example.project.feature.search.doamin.models.SearchResult

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onProductClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }

    // Definimos el degradado idéntico al que te gusta
    val headerGradient = Brush.verticalGradient(
        listOf(WoodPrimary, WoodSecondary)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fondo limpio para los resultados
    ) {
        // HEADER CON EL DEGRADADO "PRO"
        Surface(
            modifier = Modifier.fillMaxWidth(),
            // Usamos el degradado como fondo aquí
            color = Color.Transparent, // Hacemos transparente el color base
            shadowElevation = 8.dp // Elevación para que "flote"
        ) {
            // Este Box es necesario para aplicar el degradado de fondo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerGradient) // Aplicamos el degradado aquí
                    .statusBarsPadding() // Padding para la barra de estado
                    .padding(start = 4.dp, end = 16.dp, top = 12.dp, bottom = 16.dp), // Ajustamos paddings
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón de atrás (blanco para resaltar en el degradado)
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null,
                            tint = Color.White
                        )
                    }

                    // EL BUSCADOR BLANCO SÓLIDO (CÁPSULA)
                    TextField(
                        value = state.query,
                        onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp) // Un poco más alto para que se vea imponente
                            .focusRequester(focusRequester),
                        placeholder = {
                            Text("Buscar muebles...", color = Color.Gray, fontSize = 14.sp)
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, null, tint = Color.Gray)
                        },
                        shape = RoundedCornerShape(12.dp), // Esquinas originales
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent, // Sin línea de abajo
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            cursorColor = WoodPrimary
                        ),
                        singleLine = true
                    )
                }
            }
        }

        // Lista de resultados
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.results) { result ->
                SearchResultItem(
                    result = result,
                    onClick = { onProductClick(result.productId) }
                )
            }
        }
    }
}

@Composable
fun SearchResultItem(
    result: SearchResult,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(4.dp, RoundedCornerShape(16.dp), clip = true),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = WoodSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(12.dp),
                color = WoodPrimary.copy(alpha = 0.1f),
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Chair,
                        contentDescription = null,
                        tint = WoodPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del producto
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = result.title,
                    fontWeight = FontWeight.Bold,
                    color = WoodTextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Badge de categoría
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = WoodPrimary.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = result.category,
                            fontSize = 10.sp,
                            color = WoodPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Text(
                        text = "•",
                        color = WoodTextHint,
                        fontSize = 10.sp
                    )

                    Text(
                        text = result.color,
                        fontSize = 11.sp,
                        color = WoodTextSecondary,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Precio
            Text(
                text = "$${result.price}",
                fontWeight = FontWeight.ExtraBold,
                color = WoodPrimary,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = WoodTextHint,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}