package ru.itabrek.courses.auth

import com.fasterxml.jackson.databind.ObjectMapper
import org.modelmapper.ModelMapper
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
import ru.itabrek.courses.dto.*
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.entity.UserData
import ru.itabrek.courses.service.UserDataService
import ru.itabrek.courses.service.UserService
import java.security.Principal


@Service
class AuthService(
    private val userService: UserService,
    private val userDataService: UserDataService,
    private val accessTokenService: AccessTokenService,
    private val refreshTokenService: RefreshTokenService,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper: ModelMapper = ModelMapper()

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
        return ResponseEntity<JwtAuthResponse>(JwtAuthResponse(access = access, refresh = refresh), HttpStatus.CREATED)
    }

    fun login(request: UserRequest): UserLoginResponse {
        logger.info("LOGIN::USER LOGIN PROCESS")
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )

        val userData: UserData = userDataService.getUserDataByUsername(request.username)

        val access = accessTokenService.generateToken(auth.principal as UserDetails)
        val refresh = refreshTokenService.generateToken(auth.principal as UserDetails)

        logger.info("LOGIN::USER SUCCESSFULLY LOGIN")
        return UserLoginResponse(user = userData, access = access, refresh = refresh)

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
        return ResponseEntity<JwtAuthResponse>(JwtAuthResponse(access = newAccess, refresh = newRefresh), HttpStatus.OK)
    }
}
