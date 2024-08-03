package ru.itabrek.courses.service

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.itabrek.courses.entity.Course
import ru.itabrek.courses.entity.Lesson
import ru.itabrek.courses.exceptions.CourseNotFoundException
import ru.itabrek.courses.repository.CourseRepository
import ru.itabrek.courses.repository.LessonRepository
import java.security.Principal
import kotlin.jvm.optionals.getOrNull

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val entityManager: EntityManager,
    private val userService: UserService
) {

    @Transactional
    fun save(request: Course, principal: Principal): Course {
        if (request.id == null) {
            request.apply {
                id = userService.findByUsername(principal.name).id
            }
        }
        return entityManager.merge(request)
    }

    fun getAllCourses(): Iterable<Course> {
        return courseRepository.findAll()
    }

    fun getById(id: Long): Course? {
        return courseRepository.findById(id).getOrNull()
    }
}