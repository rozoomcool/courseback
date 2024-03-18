package ru.itabrek.courses.auth

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.itabrek.courses.dto.JwtAuthResponse
import ru.itabrek.courses.dto.UserCreateRequest
import ru.itabrek.courses.dto.UserRequest
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.service.UserService


@Service
class AuthService(
    private val userService: UserService,
    private val accessTokenService: AccessTokenService,
    private val refreshTokenService: RefreshTokenService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun create(request: UserCreateRequest): ResponseEntity<JwtAuthResponse> {
        logger.info("CREATE::USER CREATE PROCESS")
        if (userService.userExists(request.username)) {
            logger.info("CREATE::USER ALREADY EXISTS")
            return ResponseEntity<JwtAuthResponse>(null, HttpStatus.CONFLICT)
        }

        val user: User
        try {
            val userToCreate = User(
                username = request.username,
                password = passwordEncoder.encode(request.password),
                role = request.role
            )
            user = userService.create(userToCreate)
        } catch (e: Exception) {
            logger.info("CREATE::BAD REQUEST: $e")
            return ResponseEntity<JwtAuthResponse>(null, HttpStatus.BAD_REQUEST)
        }

        val access = accessTokenService.generateToken(user)
        val refresh = refreshTokenService.generateToken(user)

        logger.info("CREATE::USER SUCCESSFULLY CREATED")
        return ResponseEntity<JwtAuthResponse>(JwtAuthResponse(access, refresh), HttpStatus.CREATED)
    }

    fun login(request: UserRequest): ResponseEntity<JwtAuthResponse> {
        logger.info("LOGIN::USER LOGIN PROCESS")
        try {
            val auth = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.username,
                    request.password
                )
            )

            val access = accessTokenService.generateToken(auth.principal as UserDetails)
            val refresh = refreshTokenService.generateToken(auth.principal as UserDetails)

            logger.info("LOGIN::USER SUCCESSFULLY LOGIN")
            return ResponseEntity(JwtAuthResponse(access, refresh), HttpStatus.OK)
        } catch (e: UsernameNotFoundException) {
            logger.info("LOGIN::USER NOT FOUND EXCEPTION: $e")
            return ResponseEntity(null, HttpStatus.NOT_FOUND)
        }catch (e: AuthenticationException) {
            logger.info("LOGIN::AUTHENTICATION EXCEPTION: $e")
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            logger.info("LOGIN::UNEXPECTED ERROR: $e")
            return ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    fun refresh(refresh: String?): ResponseEntity<JwtAuthResponse> {
        logger.info("REFRESH::USER REFRESH PROCESS")
        val username = refreshTokenService.extractUsername(refresh!!)
        val user = userService
            .userDetailsService()
            .loadUserByUsername(username)
        if (!refreshTokenService.isTokenValid(refresh, user)) {
            logger.info("REFRESH::BAD_REQUEST REFRESH PROCESS")
            return ResponseEntity<JwtAuthResponse>(null, HttpStatus.BAD_REQUEST)
        }
        val newAccess = accessTokenService.generateToken(user)
        val newRefresh = refreshTokenService.generateToken(user)

        logger.info("REFRESH::BAD_REQUEST REFRESH PROCESS SUCCESSFUL")
        return ResponseEntity<JwtAuthResponse>(JwtAuthResponse(newAccess, newRefresh), HttpStatus.OK)
    }
}
