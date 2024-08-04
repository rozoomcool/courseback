package ru.itabrek.courses.service

import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.itabrek.courses.dto.UserUpdateDto
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.entity.UserData
import ru.itabrek.courses.exceptions.UserNotFoundException
import ru.itabrek.courses.model.Role
import ru.itabrek.courses.repository.UserDataRepository
import ru.itabrek.courses.repository.UserRepository
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userDataRepository: UserDataRepository
): UserDetailsService {

    @Transactional
    fun create(user: User): User {
        val userToSave = User(
            username = user.username,
            password = user.password,
            role = user.role
        )
        val saved = userRepository.save(userToSave)
        userDataRepository.save(UserData(user = saved))

        return saved
    }

    fun userExists(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun getAll(): Iterable<User> {
        return userRepository.findAll()
    }

    fun getById(id: Long): User? {
        return userRepository.findById(id).getOrNull()
    }

    fun findByUsername(username: String): User {
        return userRepository.findByUsername(username) ?: throw UserNotFoundException("User not found")
    }

    fun userDetailsService(): UserDetailsService = UserDetailsService { username -> this.loadUserByUsername(username) }

    fun getCurrentUser(): User {
//        return SecurityContextHolder.getContext().authentication.principal as User
        val username = SecurityContextHolder.getContext().authentication.name
        return findByUsername(username)
    }

    fun updateUser(oldUser: User, user: UserUpdateDto): User {
        val entity = oldUser.apply {
            firstname = user.firstname
            lastname = user.lastname
            surname = user.surname
            avatar = user.avatar
        }
        return userRepository.save(entity)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")
    }
}