package ru.itabrek.courses.dto

import ru.itabrek.courses.entity.User

data class JwtAuthResponse(
    val user: User? = null,
    val access: String,
    val refresh: String
)