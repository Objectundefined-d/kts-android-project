package com.example.kts_project.presentation.navigation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kts_project.presentation.screens.greetingscreen.GreetingScreen
import com.example.kts_project.presentation.screens.loginscreen.LoginScreen
import com.example.kts_project.presentation.screens.mainscreen.MainScreen
import com.example.kts_project.presentation.viewmodel.loginviewmodel.LoginViewModel
import com.example.kts_project.presentation.viewmodel.mainviewmodel.MainViewModel
import kotlinx.serialization.Serializable

@Serializable
object Main

@Serializable
object Login

@Serializable
object Greeting

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Greeting
    ) {
        composable<Main> {
            MainScreen(
                onBack = {
                    (navController.context.getActivity() as? Activity)?.finish()
                },
                viewModel = mainViewModel
            )
        }
        composable<Login> {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Main) {
                    popUpTo(Login) { inclusive = true }
                }
            },
                onBack = {
                    navController.navigate(Greeting)
                },
                viewModel = loginViewModel)
        }

        composable<Greeting> {
            GreetingScreen(onNavigateToLogin = {
                navController.navigate(Login)
            })
        }
    }
}

fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
}