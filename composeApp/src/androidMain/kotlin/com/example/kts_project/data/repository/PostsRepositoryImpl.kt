package com.example.kts_project.data.repository

import com.example.kts_project.domain.model.Post
import com.example.kts_project.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(): PostsRepository {

    val mockPosts = List(20) { index ->
        Post(
            id = index,
            title = "Заголовок поста ${index + 1}",
            description = "Это подробное описание поста номер ${index + 1}. Здесь может быть интересный контент, который пользователь захочет прочитать. В описании может быть много текста, чтобы проверить отображение многострочного контента.",
            author = "user_${index % 5}",
            likes = (50 + index * 7) % 1000,
            comments = (10 + index * 3) % 200,
            imageUrl = if (index % 3 == 0) "https://picsum.photos/200/150?random=$index" else null,
            timestamp = System.currentTimeMillis() - (index * 3600000)
        )
    }

    override fun getPosts(): Flow<List<Post>> = flow {
        emit(mockPosts)
    }

    override suspend fun refreshPosts(): Result<List<Post>> {
        return try {
            Result.success(mockPosts.shuffled())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}