package ru.itabrek.courses.dto

import ru.itabrek.courses.model.ContentType

data class StageRequest(
    val contentType: ContentType,
    val content: String
)