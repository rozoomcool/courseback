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
import kotlin.jvm.optionals.getOrNull

@Component
class FileUploadUtil {

    companion object {
//        @Value("\${upload.path}")
//        val rootDir: String? = null
//
//        val uploadPath: Path? = Path.of(rootDir)
//
        fun getExtensionByStringHandling(filename: String): String {
            return filename.substring(filename.lastIndexOf(".") + 1)
        }
//
    }

//    fun saveFile(file: MultipartFile): String {
//        if (file.isEmpty) {
//            throw StorageException("Failed to store empty file.");
//        }
//
//        val extension: String = getExtensionByStringHandling(file.name);
//        val filename: String = "${UUID.randomUUID()}.$extension"
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath)
//        }
//
//        try {
//            val inputStream: InputStream = file.inputStream
//            val filePath: Path = uploadPath!!.resolve(filename)
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
//
//            return filename
//        } catch (ioe: IOException) {
//            throw IOException("Could not save file: " + filename, ioe)
//        }
//
//    }
}
