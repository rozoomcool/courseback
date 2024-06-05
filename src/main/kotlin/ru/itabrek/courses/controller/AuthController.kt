package ru.itabrek.courses.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itabrek.courses.auth.AuthService
import ru.itabrek.courses.dto.*
import ru.itabrek.courses.entity.User

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/login")
    fun login(@RequestBody userRequest: UserRequest): ResponseEntity<JwtAuthResponse> {
        logger.info("AUTH/LOGIN")
        try {
            return ResponseEntity.ok(authService.login(userRequest))
        } catch (e: UsernameNotFoundException) {
            logger.info("LOGIN::USER NOT FOUND EXCEPTION: $e")
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        } catch (e: AuthenticationException) {
            logger.info("LOGIN::AUTHENTICATION EXCEPTION: $e")
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            logger.info("LOGIN::UNEXPECTED ERROR: $e")
            return ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
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