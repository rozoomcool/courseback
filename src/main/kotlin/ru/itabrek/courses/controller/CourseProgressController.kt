package ru.itabrek.courses.controller

import org.springframework.web.bind.annotation.*
import ru.itabrek.courses.entity.CourseProgress
import ru.itabrek.courses.entity.CourseProgressType
import ru.itabrek.courses.service.CourseProgressService

@RestController
@RequestMapping("/api/v1/progress")
class CourseProgressController(
        private val courseProgressService: CourseProgressService
) {
    @GetMapping
    fun getStatistic(@RequestBody stats: GetStats): Iterable<CourseProgress> {
        return courseProgressService.getStatistics(stats.courseId, stats.userId)
    }

    @PostMapping
    fun add(@RequestBody addStats: AddStats) {
        courseProgressService.add(addStats.courseId, addStats.userId, addStats.materialId, addStats.type)
    }
}

class GetStats(
        var userId: Long, var courseId: Long
)

class AddStats(
        var userId: Long, var courseId: Long, var materialId: Long, var type: CourseProgressType
)