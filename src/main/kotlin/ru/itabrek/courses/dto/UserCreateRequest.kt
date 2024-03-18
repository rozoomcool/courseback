package ru.itabrek.courses.dto

import ru.itabrek.courses.model.Role


data class UserCreateRequest(
    val username: String,
    val password: String,
    val role: Role
)