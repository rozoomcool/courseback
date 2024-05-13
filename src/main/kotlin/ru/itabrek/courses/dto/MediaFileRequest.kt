package ru.itabrek.courses.dto

import org.springframework.web.multipart.MultipartFile

data class MediaFileRequest(
        val file: MultipartFile,
        val fileName: String
)