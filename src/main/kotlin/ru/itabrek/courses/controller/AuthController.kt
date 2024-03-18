package ru.itabrek.courses.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itabrek.courses.auth.AuthService
import ru.itabrek.courses.dto.AuthRefreshRequest
import ru.itabrek.courses.dto.JwtAuthResponse
import ru.itabrek.courses.dto.UserCreateRequest
import ru.itabrek.courses.dto.UserRequest

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/login")
    fun login(@RequestBody userRequest: UserRequest): ResponseEntity<JwtAuthResponse> {
        logger.info("AUTH/LOGIN")
        return authService.login(userRequest)
    }

    @PostMapping("/create")
    fun create(@RequestBody userCreateRequest: UserCreateRequest): ResponseEntity<JwtAuthResponse> {
        logger.info("AUTH/CREATE")
        return authService.create(userCreateRequest)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: AuthRefreshRequest): ResponseEntity<JwtAuthResponse> {
        logger.info("AUTH/REFRESH")
        return authService.refresh(request.refresh)
    }
}