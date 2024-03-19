package ru.itabrek.courses.dto

import ru.itabrek.courses.model.Complexity

data class CourseRequest(
    val id: Long,
    val title: String,
    val description: String,
    val complexity: Complexity,
    val lessons: List<LessonRequest>
)