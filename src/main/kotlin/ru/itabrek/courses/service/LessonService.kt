package ru.itabrek.courses.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.itabrek.courses.dto.LessonRequest
import ru.itabrek.courses.entity.Course
import ru.itabrek.courses.entity.Lesson
import ru.itabrek.courses.repository.LessonRepository

@Service
class LessonService(
    private val lessonRepository: LessonRepository,
    private val stageService: StageService
) {
}