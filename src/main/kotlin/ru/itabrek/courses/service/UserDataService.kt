package ru.itabrek.courses.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.itabrek.courses.dto.UserDataRequest
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.entity.UserData
import ru.itabrek.courses.exceptions.UserNotFoundException
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

    fun getUserData(id: Long? = null): UserData {
        if (id == null) {
            return userDataRepository.findByUser(userService.getCurrentUser()) ?: throw UserNotFoundException("User Not Found")
        }
        val userData = userDataRepository.findById(id).getOrNull()
        return userData ?: throw UserNotFoundException("User Not Found")
    }

    fun getUserDataByUsername(username: String? = null): UserData {
        if (username == null) {
            return userDataRepository.findByUser(userService.getCurrentUser()) ?: throw UserNotFoundException("User Not Found")
        }
        val userData = userDataRepository.findByUserUsername(username)
        return userData ?: throw UserNotFoundException("User Not Found")
    }
}