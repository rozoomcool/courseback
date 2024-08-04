package ru.itabrek.courses.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.itabrek.courses.entity.CourseProgress
import ru.itabrek.courses.entity.CourseProgressType

@Repository
interface CourseProgressRepository : CrudRepository<CourseProgress, Long> {
    fun findAllByCourseIdAndUserId(courseId: Long, userId: Long): Iterable<CourseProgress>
    fun existsByCourseIdAndUserIdAndMaterialIdAndType(courseId: Long, userId: Long, materialId: Long, type: CourseProgressType): Boolean
}