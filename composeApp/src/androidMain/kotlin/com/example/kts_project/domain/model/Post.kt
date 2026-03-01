package com.example.kts_project.domain.model

data class Post (
    val id: Int,
    val title: String,
    val description: String,
    val author: String,
    val likes: Int,
    val comments: Int,
    val imageUrl: String?,
    val timestamp: Long = System.currentTimeMillis()
)