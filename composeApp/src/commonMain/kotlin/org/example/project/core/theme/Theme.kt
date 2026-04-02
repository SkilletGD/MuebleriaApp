package org.example.project.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val WoodColorScheme = lightColorScheme(
    primary = WoodPrimary,
    secondary = WoodSecondary,
    background = WoodBackground,
    surface = WoodSurface,
    error = WoodError,
    onPrimary = Color.White,
    onBackground = WoodTextPrimary,
    onSurface = WoodTextPrimary,
)
@Composable
fun WoodcraftTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WoodColorScheme,
        // Puedes agregar Typography aquí después
        content = content
    )
}