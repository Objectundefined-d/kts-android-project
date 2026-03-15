package com.example.kts_project.domain.model

sealed class AuthError : Exception() {
    class EmptyEmail : AuthError()
    class EmptyPassword : AuthError()
}