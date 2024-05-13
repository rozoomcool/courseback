package ru.itabrek.courses.service

import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.itabrek.courses.entity.MediaFile
import ru.itabrek.courses.repository.MediaFileRepository
import java.nio.file.Path
import java.util.stream.Stream

@Service
class MediaFileService(
        private val mediaFileRepository: MediaFileRepository
): StorageService {
    fun save(multipartFile: MultipartFile) {
        mediaFileRepository.save(MediaFile(
                fileName = multipartFile.name
        ))
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun store(file: MultipartFile?) {
        TODO("Not yet implemented")
    }

    override fun loadAll(): Stream<Path?>? {
        TODO("Not yet implemented")
    }

    override fun load(filename: String?): Path? {
        TODO("Not yet implemented")
    }

    override fun loadAsResource(filename: String?): Resource? {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}