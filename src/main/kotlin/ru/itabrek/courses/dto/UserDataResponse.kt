package ru.itabrek.courses.dto

import org.apache.logging.log4j.util.StringMap
import java.util.Date

data class UserDataResponse(
    val user: UserResponse,
    val firstname: String,
    val lastname: String,
    val dateOfBirth: Date,
    val phone: String,
    val createdAt: Date,
    val updatedAt: Date
)
