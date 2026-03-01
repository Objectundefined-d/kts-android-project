package com.example.kts_project.data.repository

import com.example.kts_project.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(): LoginRepository {
    override suspend fun login(userName: String, password: String): Result<Unit> {
        return when {
            userName.isBlank() -> {
                Result.failure(IllegalArgumentException("Введите email"))
            }
            password.isBlank() -> {
                Result.failure(IllegalArgumentException("Введите пароль"))
            }
            else -> {
                Result.success(Unit)
            }
        }
    }
}