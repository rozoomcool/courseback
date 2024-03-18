package ru.itabrek.courses.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.entity.UserData

@Repository
interface UserDataRepository: CrudRepository<UserData, Long> {
    fun findByUser(user: User): UserData?
}