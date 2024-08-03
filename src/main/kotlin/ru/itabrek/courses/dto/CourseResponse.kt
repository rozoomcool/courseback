//package ru.itabrek.courses.dto
//
//import ru.itabrek.courses.entity.Course
//import ru.itabrek.courses.model.Complexity
//import java.util.Date
//
//data class CourseResponse(
//    val id: Long,
//    val title: String,
//    val description: String,
//    val complexity: Complexity,
//    val teacherId: Long,
//    val lessons: List<LessonResponse>,
//    val createdAt: Date,
//    val updatedAt: Date
//) {
//    companion object {
//        fun loadFrom(course: Course): CourseResponse {
//            return CourseResponse(
//                id = course.id!!,
//                title = course.title,
//                description = course.description,
//                complexity = course.complexity,
//                teacherId = course.owner!!.id!!,
//                lessons = course.chapters.map { lesson ->
//                    LessonResponse(
//                        id = lesson.id!!,
//                        title = lesson.title,
//                        stages = lesson.stages.map { stage ->
//                            StageResponse(
//                                id = stage.id!!,
//                                contentType = stage.contentType,
//                                content = stage.content,
//                                createdAt = stage.createdAt!!,
//                                updatedAt = stage.updatedAt!!
//                            )
//                        },
//                        createdAt = lesson.createdAt!!,
//                        updatedAt = lesson.updatedAt!!
//                    )
//                },
//                createdAt = course.createdAt!!,
//                updatedAt = course.updatedAt!!
//            )
//        }
//    }
//
//}