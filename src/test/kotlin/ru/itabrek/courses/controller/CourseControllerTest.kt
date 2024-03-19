package ru.itabrek.courses.controller

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CourseControllerTest {

    @Test
    @Order(1)
    fun createCourse() {
    }

    @Test
    @Order(2)
    fun getAllCourses() {
    }

    @Test
    @Order(3)
    fun updateCourse() {
    }
}