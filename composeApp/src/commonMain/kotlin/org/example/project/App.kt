package org.example.project

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.core.theme.WoodcraftTheme
import org.example.project.core.ui.components.MainScaffold

@Composable
@Preview
fun App() {
    WoodcraftTheme {
        MainScaffold()
    }
}