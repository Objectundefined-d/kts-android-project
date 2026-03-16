package com.example.kts_project.data.remote

import coil3.network.NetworkClient
import com.example.kts_project.data.remote.model.CoursesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class StepikApi(private val client: HttpClient) {

    suspend fun getCourses(page: Int, search: String? = null) : CoursesResponse {
        return client.get("courses") {
            parameter("page", page)
            parameter("is_public", true)
            if (search != null) parameter("search", search)
        }.body()
    }

    suspend fun getCourseById(id: Int) : CoursesResponse {
        return client.get("courses/$id").body()
    }
}