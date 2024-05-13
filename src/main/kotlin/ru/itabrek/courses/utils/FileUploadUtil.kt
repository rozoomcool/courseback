package ru.itabrek.courses.utils;

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import ru.itabrek.courses.exceptions.StorageException
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Component
class FileUploadUtil {

    companion object {
        @Value("#{upload.path}")
        private const val uploadPath: String = "C:\\Users\\User\\PycharmProjects\\deepfakebot\\courseback\\src\\main\\resources\\media"

    }

    fun saveFile(file: MultipartFile): String {
        if (file.isEmpty) {
            throw StorageException("Failed to store empty file.");
        }
        val destinationFile: Path = Paths.get(FileUploadUtil.uploadPath).resolve(
                Paths.get(file.originalFilename))
                .normalize().toAbsolutePath()
        val filename: String = "${UUID.randomUUID()}.${file.contentType?.split(" / ")?.get(1)}"
        val uploadPath: Path = Paths.get(FileUploadUtil.uploadPath)

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }

        try {
            val inputStream: InputStream = file.inputStream
            val filePath: Path = uploadPath.resolve(filename)
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
        } catch (ioe: IOException) {
            throw IOException("Could not save file: " + filename, ioe)
        }

        return filename
    }
}
