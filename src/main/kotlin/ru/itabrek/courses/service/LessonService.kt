package ru.itabrek.courses.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.itabrek.courses.dto.LessonRequest
import ru.itabrek.courses.entity.Course
import ru.itabrek.courses.entity.Lesson
import ru.itabrek.courses.repository.LessonRepository
import kotlin.jvm.optionals.getOrNull

@Service
class LessonService(
    private val lessonRepository: LessonRepository
) {
    fun getById(id: Long): Lesson? {
        return lessonRepository.findById(id).getOrNull()
    }
}