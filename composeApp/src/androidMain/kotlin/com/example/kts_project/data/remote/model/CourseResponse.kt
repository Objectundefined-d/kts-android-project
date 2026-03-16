package com.example.kts_project.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    val meta: Meta,
    val courses: List<RemoteCourse>,
    val enrollments: List<Int> = emptyList()
)