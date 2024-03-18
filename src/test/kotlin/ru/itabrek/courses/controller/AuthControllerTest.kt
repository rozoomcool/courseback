package ru.itabrek.courses.controller

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AuthControllerTest(
    @LocalServerPort private val port: Int
) {

    companion object {
        val TEST_USERNAME = "TEST"
        val TEST_PASSWORD = "TEST"
        val TEST_ROLE = "ADMIN"
        var ACCESS = ""
        var REFRESH = ""
    }

    @Test
    @Order(1)
    fun create() {
        val body = "{\"username\": \"$TEST_USERNAME\", \"password\": \"$TEST_PASSWORD\", \"role\": \"$TEST_ROLE\"}"
        given().port(port)
            .contentType("application/json")
            .body(body)
            .`when`().post("auth/create")
            .then().statusCode(201)
            .and().body("access", Matchers.notNullValue())
            .and().body("refresh", Matchers.notNullValue())
    }

    @Test
    @Order(2)
    fun login() {
        val body = "{\"username\": \"$TEST_USERNAME\",\"password\": \"$TEST_PASSWORD\"}"
        val response = given().port(port)
            .contentType("application/json")
            .body(body)
            .`when`().post("auth/login")

        ACCESS = response.jsonPath().get("access")
        REFRESH = response.jsonPath().get("refresh")

        response.then().statusCode(200)
            .and().body("access", Matchers.notNullValue())
            .and().body("refresh", Matchers.notNullValue())
    }

    @Test
    @Order(3)
    fun refresh() {
        val body = "{\"refresh\": \"$REFRESH\"}"
        given().port(port)
            .contentType("application/json")
            .body(body)
            .`when`().post("auth/refresh")
            .then().statusCode(200)
            .and().body("access", Matchers.notNullValue())
            .and().body("refresh", Matchers.notNullValue())
    }
}