package ru.itabrek.courses.exceptions

class StorageFileNotFoundException : StorageException {
    constructor(message: String?) : super(message, null)

    constructor(message: String?, cause: Throwable?) : super(message, cause)
}