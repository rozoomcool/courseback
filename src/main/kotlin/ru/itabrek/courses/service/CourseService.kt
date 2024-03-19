package ru.itabrek.courses.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.itabrek.courses.entity.Course
import ru.itabrek.courses.entity.Lesson
import ru.itabrek.courses.entity.Stage
import ru.itabrek.courses.exceptions.CourseNotFoundException
import ru.itabrek.courses.repository.CourseRepository
import ru.itabrek.courses.repository.LessonRepository
import ru.itabrek.courses.repository.StageRepository
import kotlin.jvm.optionals.getOrNull

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val userService: UserService,
    private val lessonRepository: LessonRepository,
    private val stageRepository: StageRepository
) {

    @Transactional
    fun save(request: Course): Course {
        val course = Course(
            id = request.id,
            title = request.title,
            description = request.description,
            teacher = request.teacher ?: userService.getCurrentUser(),
            complexity = request.complexity,
            lessons = request.lessons.map { lesson ->
                (Lesson(
                    id = lesson.id,
                    title = lesson.title,
                    stages = lesson.stages.map { stage ->
                        Stage(
                            id = stage.id,
                            contentType = stage.contentType,
                            content = stage.content
                        )
                    }.toMutableSet()
                ))
            }.toMutableSet()
        )
        return courseRepository.save(course)
    }

    fun update(request: Course): Course {
        val course =
            courseRepository.findById(request.id!!).getOrNull() ?: throw CourseNotFoundException("Course not found")

        course.apply {
            title = request.title
            description = request.description
            teacher = course.teacher ?: userService.getCurrentUser()
            complexity = request.complexity
            lessons = request.lessons.map { lesson ->
                lesson.apply {
                    title = lesson.title
                    stages = lesson.stages.map { stage ->
                        stage.apply {
                            contentType = stage.contentType
                            content = stage.content
                        }
                    }.toMutableSet()
                }
            }.toMutableSet()
        }

        return courseRepository.save(course)
    }

    fun getAllCourses(): Iterable<Course> {
        return courseRepository.findAll()
    }
}