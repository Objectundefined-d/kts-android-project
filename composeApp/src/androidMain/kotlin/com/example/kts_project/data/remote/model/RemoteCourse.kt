package com.example.kts_project.data.remote.model

import com.example.kts_project.domain.model.Course
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCourse(
    val id: Int,
    val title: String,
    val summary: String? = null,
    val cover: String? = null,
    val language: String? = null,
    val workload: String? = null,
    val description: String? = null,
    @SerialName("is_paid") val isPaid: Boolean = false,
    val price: String? = null,
    @SerialName("display_price") val displayPrice: String? = null,
    @SerialName("learners_count") val learnersCount: Int = 0,
    @SerialName("lessons_count") val lessonsCount: Int = 0,
    @SerialName("time_to_complete") val timeToComplete: Int? = null,
    @SerialName("with_certificate") val withCertificate: Boolean = false,
    @SerialName("is_popular") val isPopular: Boolean = false,
    val difficulty: String? = null,
    val slug: String? = null,
    @SerialName("canonical_url") val canonicalUrl: String? = null,
    val readiness: Double = 0.0,
    val authors: List<Int>? = null,
    val sections: List<Int>? = null,
)


fun RemoteCourse.toDomain(): Course {
    return Course(
        id = id,
        title = title,
        summary = summary,
        cover = cover,
        learnersCount = learnersCount,
        isPaid = isPaid,
        displayPrice = displayPrice,
        withCertificate = withCertificate,
        language = language,
    )
}