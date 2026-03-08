package com.example.kts_project.domain.model

data class CoursePage(
    val courses: List<Course>,
    val hasNext: Boolean,
    val currentPage: Int
)