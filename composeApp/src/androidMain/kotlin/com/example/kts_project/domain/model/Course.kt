package com.example.kts_project.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Course(
    val id: Int,
    val title: String,
    val summary: String?,
    val cover: String?,
    val learnersCount: Int,
    val isPaid: Boolean,
    val displayPrice: String?,
    val withCertificate: Boolean,
    val language: String?,
)