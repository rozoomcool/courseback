package ru.itabrek.courses.dto

import java.util.*

data class LessonResponse(
    val id: Long,
    val title: String,
    val stages: List<StageResponse>,
    val createdAt: Date,
    val updatedAt: Date
)