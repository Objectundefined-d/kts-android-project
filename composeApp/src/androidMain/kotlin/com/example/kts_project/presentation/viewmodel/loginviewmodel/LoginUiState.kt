package com.example.kts_project.presentation.viewmodel.loginviewmodel

data class LoginUiState(
    val isLoginButtonActive: Boolean = false,
    val errorType: ErrorType? = null
)

enum class ErrorType {
    EMPTY_EMAIL,
    EMPTY_PASSWORD,
    UNKNOWN_ERROR
}