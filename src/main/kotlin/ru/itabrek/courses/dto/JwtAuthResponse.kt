package ru.itabrek.courses.dto

data class JwtAuthResponse(
    val user: UserResponse? = null,
    val access: String,
    val refresh: String
)