package com.example.kts_project.domain.repository

import com.example.kts_project.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    fun getPosts(): Flow<List<Post>>

    suspend fun refreshPosts(): Result<List<Post>>
}