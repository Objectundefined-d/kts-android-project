package com.example.kts_project.presentation.viewmodel.loginviewmodel

sealed class LoginUiEvent {
    data object LoginSuccessEvent : LoginUiEvent()
}