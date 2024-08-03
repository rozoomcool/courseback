package ru.itabrek.courses.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import ru.itabrek.courses.auth.JwtAuthFilter
import ru.itabrek.courses.model.Role
import ru.itabrek.courses.service.UserService

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userService: UserService,
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { csrf -> csrf.disable() }
            .cors { cors ->
                cors.configurationSource {
                    val corsConfiguration = CorsConfiguration()
                    corsConfiguration.setAllowedOriginPatterns(listOf("*"))
                    corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    corsConfiguration.allowedHeaders = listOf("*")
                    corsConfiguration.allowCredentials = true
                    corsConfiguration
                }
            }

            .authorizeHttpRequests { request ->
                request
                    .requestMatchers(HttpMethod.POST, "/api/v1/course/**").hasAnyAuthority(Role.ADMIN.name, Role.TEACHER.name)
                    .requestMatchers(HttpMethod.PUT, "/api/v1/course/**").hasAnyAuthority(Role.ADMIN.name, Role.TEACHER.name)
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/media/**").permitAll()
                    .requestMatchers("/actuator/**").permitAll()
                    .requestMatchers("/health/**").permitAll()
                    .requestMatchers("/ping/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.getAuthenticationManager()

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userService.userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun roleHierarchy(): RoleHierarchyImpl {
        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_TEACHER \n ROLE_TEACHER > ROLE_STUDENT")
        return roleHierarchy
    }

    private fun webExpressionHandler(): SecurityExpressionHandler<FilterInvocation> {
        val defaultWebSecurityExpressionHandler = DefaultWebSecurityExpressionHandler()
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy())
        return defaultWebSecurityExpressionHandler
    }
}