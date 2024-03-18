package ru.itabrek.courses.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.itabrek.courses.entity.User

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean
}