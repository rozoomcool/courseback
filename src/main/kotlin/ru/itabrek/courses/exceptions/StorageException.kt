package ru.itabrek.courses.exceptions

open class StorageException(
        override val message: String? = null,
        cause: Throwable? = null
): Exception()