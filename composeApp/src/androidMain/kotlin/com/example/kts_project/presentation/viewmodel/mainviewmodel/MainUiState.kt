package com.example.kts_project.presentation.viewmodel.mainviewmodel

import com.example.kts_project.domain.model.Post

enum class ErrorType {
    LOAD_ERROR,
    REFRESH_ERROR,
    UNKNOWN_ERROR
}

data class MainUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorType: ErrorType? = null
)

