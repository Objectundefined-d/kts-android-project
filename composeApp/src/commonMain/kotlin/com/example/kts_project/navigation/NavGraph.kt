package com.example.kts_project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kts_project.screens.LoginScreen
import com.example.kts_project.screens.MainScreen
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Login

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Main
    ) {
        composable<Main> {
            MainScreen(
                onNavigateToLogin = {
                    navController.navigate(Login)
                }
            )
        }

        composable<Login> {
            LoginScreen()
        }
    }
}