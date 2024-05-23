package ru.itabrek.courses.exceptions

class UserNotFoundException(
        override val message: String?
): Exception()