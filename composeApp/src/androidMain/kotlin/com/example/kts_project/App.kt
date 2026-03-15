package com.example.kts_project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.kts_project.presentation.navigation.AppNavHost
import com.example.kts_project.presentation.theme.DarkColors
import com.example.kts_project.presentation.theme.LightColors

@Composable
@Preview
fun App() {
    val isDarkTheme = isSystemInDarkTheme()

    val colorScheme = if (isDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme
    ) {
        AppNavHost()
    }
}