package com.example.kts_project.data.repository

import com.example.kts_project.data.remote.StepikApi
import com.example.kts_project.data.remote.model.RemoteCourse
import com.example.kts_project.data.remote.model.CoursesResponse
import com.example.kts_project.data.remote.model.toDomain
import com.example.kts_project.domain.repository.CourseRepository
import com.example.kts_project.domain.model.Course
import com.example.kts_project.domain.model.CoursePage
import io.github.aakira.napier.Napier

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val api: StepikApi
): CourseRepository {

    override suspend fun getCourses(page: Int, search: String?): Result<CoursePage> {
        return try {
            val response = api.getCourses(page = page, search = search)
            Napier.d("Курсов получено: ${response.courses.size}", tag = "Repo")
            Result.success(
                CoursePage(
                    courses = response.courses.map { it.toDomain() },
                    hasNext = response.meta.hasNext,
                    currentPage = response.meta.page
                )
            )
        } catch (e: Exception) {
            Napier.e("Ошибка: ${e.message}", tag = "Repo")
            Result.failure(e)
        }
    }

    override suspend fun getCourseById(id: Int) : Result<Course> {
        return try {
            Result.success(api.getCourseById(id = id).courses.first().toDomain())
        } catch (e : Exception) {
            Result.failure(e)
        }
    }
}