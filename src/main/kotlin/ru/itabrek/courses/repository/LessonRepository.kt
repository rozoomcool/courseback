package ru.itabrek.courses.repository

import org.springframework.data.repository.CrudRepository
import ru.itabrek.courses.entity.Lesson

interface LessonRepository: CrudRepository<Lesson, Long> {
}