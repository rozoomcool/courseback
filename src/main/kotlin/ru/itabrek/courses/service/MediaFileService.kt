package ru.itabrek.courses.service

import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import ru.itabrek.courses.entity.MediaFile
import ru.itabrek.courses.repository.MediaFileRepository
import java.nio.file.Path
import java.util.Date
import java.util.stream.Stream

@Service
class MediaFileService(
    private val mediaFileRepository: MediaFileRepository,
    private val fileSystemStorageService: FileSystemStorageService
) {

    fun read(filename: String): Resource {
        return fileSystemStorageService.loadAsResource(filename)
    }

    fun save(multipartFile: MultipartFile): MediaFile {
        val filename = fileSystemStorageService.store(multipartFile)
        return mediaFileRepository.save(
            MediaFile(
                fileName = filename
            )
        )
    }

    fun update(mediaFile: MediaFile, file: MultipartFile): MediaFile {
        fileSystemStorageService.delete(mediaFile.fileName)
        val newFileName = fileSystemStorageService.store(file)
        return mediaFileRepository.save(mediaFile.apply { fileName = newFileName })
    }

    fun delete(mediaFile: MediaFile) {
        fileSystemStorageService.delete(mediaFile.fileName)
        mediaFileRepository.delete(mediaFile)
    }
}