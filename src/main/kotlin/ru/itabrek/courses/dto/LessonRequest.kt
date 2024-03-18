package ru.itabrek.courses.dto

data class LessonRequest(
    val title: String,
    val stages: List<StageRequest>
)