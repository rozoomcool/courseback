package ru.itabrek.courses.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.itabrek.courses.dto.CourseRequest
import ru.itabrek.courses.entity.Course
import ru.itabrek.courses.entity.Lesson
import ru.itabrek.courses.entity.Stage
import ru.itabrek.courses.repository.CourseRepository
import ru.itabrek.courses.repository.LessonRepository
import ru.itabrek.courses.repository.StageRepository

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val userService: UserService,
    private val lessonRepository: LessonRepository,
    private val stageRepository: StageRepository
) {

    @Transactional
    fun save(request: CourseRequest): Course {
        val course = Course(
            title = request.title,
            description = request.description,
            teacher = userService.getCurrentUser(),
            complexity = request.complexity,
            lessons = request.lessons.map { lessonRequest ->
                (Lesson(
                    title = lessonRequest.title,
                    stages = lessonRequest.stages.map { stageRequest ->
                        Stage(
                            contentType = stageRequest.contentType,
                            content = stageRequest.content
                        )
                    }.toMutableSet()
                ))
            }.toMutableSet()
        )
        return courseRepository.save(course)
    }

    fun getAllCourses(): Iterable<Course> {
        return courseRepository.findAll()
    }
}