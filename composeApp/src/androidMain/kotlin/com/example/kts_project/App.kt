package com.example.kts_project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.kts_project.presentation.navigation.AppNavHost


@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavHost()
    }
}