package com.example.kts_project.domain.repository

import com.example.kts_project.data.remote.model.CoursesResponse
import com.example.kts_project.domain.model.Course
import com.example.kts_project.domain.model.CoursePage

interface CourseRepository {

    suspend fun getCourses(page: Int, search: String? = null): Result<CoursePage>

    suspend fun getCourseById(id: Int): Result<Course>
}