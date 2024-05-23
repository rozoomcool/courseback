package ru.itabrek.courses.configuration

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [MethodNotAllowedException::class])
    protected fun handleMethodNotAllowedException(ex: AccessDeniedException?, request: WebRequest?): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Method Not Allowed")
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected fun handleAccessDeniedException(ex: AccessDeniedException?, request: WebRequest?): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied")
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun handleInternalServerError(ex: Exception?, request: WebRequest?): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error")
    }
}