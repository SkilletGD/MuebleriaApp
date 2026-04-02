package org.example.project.core.theme


import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Colores Woodcraft
val WoodPrimary = Color(0xFF8B5A2B)
val WoodSecondary = Color(0xFFD4A373)
val WoodBackground = Color(0xFFFEF9F0)
val WoodSurface = Color(0xFFFFFFFF)
val WoodTextPrimary = Color(0xFF2C1810)
val WoodTextSecondary = Color(0xFF8B7355)
val WoodTextHint = Color(0xFFB89A7A)
val WoodError = Color(0xFFD32F2F)
val WoodGradientBackground = Brush.verticalGradient(
    colors = listOf(WoodBackground, WoodSurface)
)