package ru.itabrek.courses.exceptions

data class CourseNotFoundException(
    override val message: String?
): Exception()