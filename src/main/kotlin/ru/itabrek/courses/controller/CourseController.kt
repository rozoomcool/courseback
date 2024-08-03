package ru.itabrek.courses.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itabrek.courses.entity.Course
import ru.itabrek.courses.service.CourseService
import java.security.Principal

@RestController
@RequestMapping("api/v1/course")
class CourseController(
    private val courseService: CourseService
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun createCourse(@RequestBody courseRequest: Course, principal: Principal): ResponseEntity<Course> {
        logger.info("COURSE/CREATE")
        try {
            return ResponseEntity(courseService.save(courseRequest, principal), HttpStatus.CREATED)
        } catch (e: Exception) {
            logger.error(e.message)
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
//
    @GetMapping
    fun getAllCourses(): ResponseEntity<Iterable<Course>> {
        logger.info("COURSE/GET_ALL")
        return ResponseEntity(courseService.getAllCourses(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Course> {
        val course = courseService.getById(id) ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(course)
    }
//
//    @PutMapping
//    fun updateCourse(@RequestBody course: Course): ResponseEntity<CourseResponse> {
//        logger.info("COURSE/UPDATE")
//        return ResponseEntity(CourseResponse.loadFrom(courseService.update(course)), HttpStatus.OK)
//    }
}