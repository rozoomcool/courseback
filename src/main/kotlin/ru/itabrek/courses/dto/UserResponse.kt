package ru.itabrek.courses.dto

import ru.itabrek.courses.model.Role
import java.util.*

class UserResponse(
        val username: String,
        val role: Role,
        val createdAt: Date?,
        val updatedAt: Date?
)