package ru.itabrek.courses.component

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ru.itabrek.courses.auth.AuthService
import ru.itabrek.courses.dto.UserCreateRequest
import ru.itabrek.courses.dto.UserRequest
import ru.itabrek.courses.model.Role
import ru.itabrek.courses.service.UserService

@Component
class DataLoader(
    private val authService: AuthService
): CommandLineRunner {
    override fun run(vararg args: String?) {
        addAdmin()
        addStudent()
        addTeacher()
    }

    fun addAdmin() {
        val resp = authService.create(UserCreateRequest(username = "admin", password = "admin", role = Role.ADMIN))
    }

    fun addStudent() {
        val resp = authService.create(UserCreateRequest(username = "student", password = "student", role = Role.STUDENT))
    }

    fun addTeacher() {
        val resp = authService.create(UserCreateRequest(username = "teacher", password = "teacher", role = Role.TEACHER))
    }
}