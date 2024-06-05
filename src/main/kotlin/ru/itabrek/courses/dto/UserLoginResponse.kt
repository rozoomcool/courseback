package ru.itabrek.courses.dto

import ru.itabrek.courses.entity.UserData

data class UserLoginResponse(
    val user: UserData,
    val access: String,
    val refresh: String,
)