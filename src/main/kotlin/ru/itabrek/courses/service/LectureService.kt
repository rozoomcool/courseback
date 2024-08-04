package ru.itabrek.courses.service

import ru.itabrek.courses.entity.Lecture
import ru.itabrek.courses.repository.LectureRepository
import kotlin.jvm.optionals.getOrNull

class LectureService(
        private val lectureRepository: LectureRepository
) {
    fun getById(id: Long): Lecture? {
        return lectureRepository.findById(id).getOrNull()
    }
}