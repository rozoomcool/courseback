package ru.itabrek.courses.dto

data class JwtAuthResponse(
    val access: String,
    val refresh: String
)