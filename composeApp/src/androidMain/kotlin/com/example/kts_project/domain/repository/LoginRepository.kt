package com.example.kts_project.domain.repository

interface LoginRepository {
    suspend fun login(userName: String, password: String): Result<Unit>
}