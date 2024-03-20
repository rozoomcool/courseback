package ru.itabrek.courses.controller

import io.restassured.RestAssured
import io.restassured.http.Header
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CourseControllerTest(
    @LocalServerPort private val port: Int
) {

    fun getAdminAuthToken(): String {
        val authBody = """
            {
                "username": "admin",
                "password": "admin"
            }
        """.trimIndent()

        val response = RestAssured.given().port(port)
            .contentType("application/json")
            .body(authBody)
            .`when`().post("auth/login")

        return response.jsonPath().get("access")
    }

    @Test
    @Order(2)
    fun createCourse() {
        val body = """
            {
                "title": "title",
                "description": "desc",
                "complexity": "EASY",
                "lessons": [
                    {
                        "title": "title",
                        "stages": [
                            {
                                "contentType": "TEXT",
                                "content": "content"
                            }
                        ]
                    }
                ]
            }
        """.trimIndent()

        RestAssured.given().port(port)
            .contentType("application/json")
            .header(Header("Authorization", "Bearer ${getAdminAuthToken()}"))
            .body(body)
            .`when`().post("course")
            .then().statusCode(201)
            .and().body("id", Matchers.notNullValue())
    }

    @Test
    @Order(3)
    fun getAllCourses() {
    }

    @Test
    @Order(4)
    fun updateCourse() {
    }
}