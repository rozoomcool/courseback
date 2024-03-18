package ru.itabrek.courses.dto

import java.util.Date

data class UserDataRequest(
    val firstname: String? = null,
    val lastname: String? = null,
    val dateOfBirth: Date? = null,
    val phone: String? = null
)