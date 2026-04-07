package org.example.project.feature.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
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
import org.example.project.feature.search.presentation.components.RecentSearchesSection

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onProductClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val headerGradient = Brush.verticalGradient(listOf(WoodPrimary, WoodSecondary))

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // --- HEADER (Sin cambios) ---
        Surface(modifier = Modifier.fillMaxWidth(), color = Color.Transparent, shadowElevation = 8.dp) {
            Box(modifier = Modifier.fillMaxWidth().background(headerGradient).statusBarsPadding()
                .padding(start = 4.dp, end = 16.dp, top = 12.dp, bottom = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White) }
                    TextField(
                        value = state.query,
                        onValueChange = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
                        modifier = Modifier.weight(1f).height(50.dp).focusRequester(focusRequester),
                        placeholder = { Text("Buscar muebles...", color = Color.Gray, fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.Gray) },
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
            }
        }

        // --- CONTENIDO DINÁMICO ---
        if (state.query.isEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                // Categorías
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Explorar categorías", style = MaterialTheme.typography.titleSmall, color = WoodTextSecondary, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        CategoryChipsRow { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) }
                    }
                }
                // Historial usando tu componente (sin padding extra aquí)
                if (state.recentSearches.isNotEmpty()) {
                    item {
                        RecentSearchesSection(
                            recentSearches = state.recentSearches,
                            onSearchClick = { viewModel.onEvent(SearchEvent.OnQueryChanged(it)) },
                            onDeleteClick = { viewModel.onEvent(SearchEvent.OnDeleteRecentSearch(it)) }
                        )
                    }
                }
            }
        } else {
            // RESULTADOS DE BÚSQUEDA
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.results) { result ->
                    SearchResultItem(result = result, onClick = {
                        viewModel.onEvent(SearchEvent.OnProductClicked(result.productId))
                        onProductClick(result.productId)
                    })
                }
            }
        }
    }
}

// --- COMPONENTES AUXILIARES ---

@Composable
fun CategoryChipsRow(onCategoryClick: (String) -> Unit) {
    val categories = listOf("Salas", "Comedores", "Recámaras", "Oficina", "Cocina")
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { cat ->
            SuggestionChip(
                onClick = { onCategoryClick(cat) },
                label = { Text(cat) },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    labelColor = WoodPrimary,
                    containerColor = WoodPrimary.copy(alpha = 0.05f)
                ),
                border = SuggestionChipDefaults.suggestionChipBorder(enabled = true, borderColor = WoodPrimary.copy(0.2f))
            )
        }
    }
}

@Composable
fun RecentSearchItem(
    query: String,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.History, null, tint = WoodTextHint, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(16.dp))
        Text(text = query, color = WoodTextPrimary, modifier = Modifier.weight(1f))
        IconButton(onClick = onDeleteClick, modifier = Modifier.size(24.dp)) {
            Icon(Icons.Default.Close, null, tint = WoodTextHint, modifier = Modifier.size(16.dp))
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