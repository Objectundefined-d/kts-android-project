package com.example.kts_project.presentation.viewmodel.loginviewmodel

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
    val error: String? = null
)