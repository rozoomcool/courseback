package ru.itabrek.courses.dto

import ru.itabrek.courses.model.ContentType
import java.util.Date

data class StageResponse(
    val id: Long,
    val contentType: ContentType,
    val content: String,
    val createdAt: Date,
    val updatedAt: Date
)