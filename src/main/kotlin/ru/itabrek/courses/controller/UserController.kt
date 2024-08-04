package ru.itabrek.courses.controller

import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itabrek.courses.dto.UserResponse
import ru.itabrek.courses.dto.UserUpdateDto
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.service.UserService
import java.security.Principal

@RestController
@RequestMapping("api/v1/user")
class UserController(
    private val userService: UserService,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper: ModelMapper = ModelMapper()

    @GetMapping
    fun getUser(): ResponseEntity<User> {
        logger.info("USER/GET_USER")
        return ResponseEntity.ok(userService.getCurrentUser())
    }

    @GetMapping("/all")
    fun getAll(): ResponseEntity<UserResponse> {
        logger.info("USER/GET_ALL")
        return ResponseEntity.ok(modelMapper.map(userService.getCurrentUser(), UserResponse::class.java))
    }

    @PutMapping()
    fun update(user: UserUpdateDto, principal: Principal): ResponseEntity<User> {
        logger.info("USER/UPDATE")
        try {
            val oldUser = userService.findByUsername(principal.name)
            return ResponseEntity.ok(userService.updateUser(oldUser, user))
        } catch (e: Exception) {
            logger.error(e.message)
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }
}