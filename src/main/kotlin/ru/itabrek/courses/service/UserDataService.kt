package ru.itabrek.courses.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.itabrek.courses.dto.UserDataRequest
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.entity.UserData
import ru.itabrek.courses.repository.UserDataRepository
import kotlin.jvm.optionals.getOrNull

@Service
class UserDataService(
    private val userDataRepository: UserDataRepository,
    private val userService: UserService
) {

    fun create(request: UserDataRequest) {
        val created = UserData(
            firstname = request.firstname,
            lastname = request.lastname,
            user = userService.getCurrentUser(),
            dateOfBirth = request.dateOfBirth,
            phone = request.phone
        )
        userDataRepository.save(created)
    }

    fun update(request: UserDataRequest): UserData {
        return userDataRepository.save(
            UserData(
                firstname = request.firstname,
                lastname = request.lastname,
                dateOfBirth = request.dateOfBirth,
                user = userService.getCurrentUser(),
                phone = request.phone
            )
        )
    }

    fun getUserData(id: Long? = null): ResponseEntity<UserData> {
        if (id == null) {
            val userData = userDataRepository.findByUser(userService.getCurrentUser())
            return ResponseEntity(userData, if (userData != null) HttpStatus.FOUND else HttpStatus.NOT_FOUND)
        }
        val userData = userDataRepository.findById(id).getOrNull()
        return ResponseEntity(userData, if (userData != null) HttpStatus.FOUND else HttpStatus.NOT_FOUND)
    }
}