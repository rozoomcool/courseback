package ru.itabrek.courses.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itabrek.courses.dto.CourseRequest
import ru.itabrek.courses.entity.Course
import ru.itabrek.courses.service.CourseService

@RestController
@RequestMapping("/course")
class CourseController(
    private val courseService: CourseService
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun createCourse(@RequestBody courseRequest: CourseRequest): ResponseEntity<Course> {
        logger.info("COURSE/CREATE")
        return ResponseEntity(courseService.save(courseRequest), HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllCourses(): ResponseEntity<Iterable<Course>> {
        logger.info("COURSE/GET_ALL")
        return ResponseEntity(courseService.getAllCourses(), HttpStatus.OK)
    }
}