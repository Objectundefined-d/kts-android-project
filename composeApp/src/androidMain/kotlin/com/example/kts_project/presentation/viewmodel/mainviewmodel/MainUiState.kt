package com.example.kts_project.presentation.viewmodel.mainviewmodel

import com.example.kts_project.domain.model.Course

enum class ErrorType {
    LOAD_ERROR,
    REFRESH_ERROR,
    UNKNOWN_ERROR
}

data class MainUiState(
    val courses: List<Course> = emptyList(),
    val selectedCourse: Course? = null,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasNext: Boolean = false,
    val currentPage: Int = 1,
    val errorType: ErrorType? = null
)

