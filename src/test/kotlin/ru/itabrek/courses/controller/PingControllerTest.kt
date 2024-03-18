package ru.itabrek.courses.controller

import org.junit.jupiter.api.Test

import io.restassured.RestAssured.given
import org.hamcrest.core.IsEqual.equalTo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PingControllerTest(
    @LocalServerPort private val port: Int
) {
    @Test
    fun ping() {
        given().port(port).get("/ping")
            .then().statusCode(200)
            .and().body(equalTo("pong"))
    }
}