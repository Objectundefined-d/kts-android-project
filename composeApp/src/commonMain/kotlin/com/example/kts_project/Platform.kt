package com.example.kts_project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform