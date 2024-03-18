package ru.itabrek.courses

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.yaml.snakeyaml.nodes.Tag.STR
import ru.itabrek.courses.auth.AuthService
import ru.itabrek.courses.dto.UserRequest
import ru.itabrek.courses.entity.User
import ru.itabrek.courses.model.Role
import ru.itabrek.courses.service.UserService

@SpringBootApplication
class CoursesApplication {
//	@Bean
//	fun commandLineRunner(jdbc: JdbcConnectionDetails): CommandLineRunner {
//		return CommandLineRunner {
//			val details = """
//		class: ${jdbc.javaClass.name}
//		JDBC URL: ${jdbc.jdbcUrl}
//		Username: ${jdbc.username}
//		Password: ${jdbc.password}
//	"""
//
//			println(details)
//		}
//	}
}

fun main(args: Array<String>) {
	runApplication<CoursesApplication>(*args)
}