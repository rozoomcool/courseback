package ru.itabrek.courses.service

import org.springframework.stereotype.Service
import ru.itabrek.courses.entity.CourseProgress
import ru.itabrek.courses.entity.CourseProgressType
import ru.itabrek.courses.repository.CourseProgressRepository

@Service
class CourseProgressService(
        private val courseProgressRepository: CourseProgressRepository
) {

    fun add(courseId: Long, userId: Long, materialId: Long, materialType: CourseProgressType) {
        if (!courseProgressRepository.existsByCourseIdAndUserIdAndMaterialIdAndType(
                courseId, userId, materialId, materialType
        )) {
            courseProgressRepository.save(CourseProgress(userId = userId, courseId = courseId, materialId = materialId, type = materialType))
        }
    }

    fun getStatistics(courseId: Long, userId: Long): Iterable<CourseProgress> {
        return courseProgressRepository.findAllByCourseIdAndUserId(courseId, userId)
    }

}