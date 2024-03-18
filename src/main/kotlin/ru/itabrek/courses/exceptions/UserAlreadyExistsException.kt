package ru.itabrek.courses.exceptions

class UserAlreadyExistsException(
    override val message: String?
): Exception()