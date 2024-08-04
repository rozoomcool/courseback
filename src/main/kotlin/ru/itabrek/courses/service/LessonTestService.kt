package ru.itabrek.courses.service

import ru.itabrek.courses.entity.LessonTest
import ru.itabrek.courses.repository.LessonTestRepository
import kotlin.jvm.optionals.getOrNull

class LessonTestService(
        private val lessonTestRepository: LessonTestRepository
) {
    fun getById(id: Long): LessonTest? {
        return lessonTestRepository.findById(id).getOrNull()
    }
}