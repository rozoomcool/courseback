package ru.itabrek.courses.controller

import org.modelmapper.ModelMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itabrek.courses.dto.UserDataRequest
import ru.itabrek.courses.dto.UserDataResponse
import ru.itabrek.courses.entity.UserData
import ru.itabrek.courses.service.UserDataService

@RestController
@RequestMapping("/user/details")
class UserDataController(
        private val userDataService: UserDataService,
        private val modelMapper: ModelMapper = ModelMapper()
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun getUserData(): ResponseEntity<UserDataResponse> {
        logger.info("USER_DATA/GET_USER_DATA")
        return ResponseEntity.ok(modelMapper.map(userDataService.getUserData(), UserDataResponse::class.java))
    }

    @PutMapping
    fun updateUserData(@RequestBody userDataRequest: UserDataRequest): ResponseEntity<UserData> {
        logger.info("USER_DATA/UPDATE_USER_DATA")
        return try {
            val userData = userDataService.update(userDataRequest)
            logger.info("USER_DATA/UPDATE_USER_DATA SUCCESSFULLY")
            ResponseEntity(userData, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("USER_DATA/UPDATE_USER_DATA ERROR: $e")
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
}