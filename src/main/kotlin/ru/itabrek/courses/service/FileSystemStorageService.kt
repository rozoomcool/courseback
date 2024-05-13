package ru.itabrek.courses.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.MimeType
import org.springframework.web.multipart.MultipartFile
import ru.itabrek.courses.exceptions.StorageException
import ru.itabrek.courses.exceptions.StorageFileNotFoundException
import ru.itabrek.courses.utils.FileUploadUtil
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Stream


@Service
class FileSystemStorageService(
    @Value("\${upload.path}")
    var rootDir: String
) : StorageService {

    val uploadPath: Path = Path.of(rootDir)

    init {
        try {
            Files.createDirectories(uploadPath)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    override fun store(file: MultipartFile): String {
        if (file.isEmpty) {
            throw StorageException("Failed to store empty file.");
        }
        if(file.originalFilename == null) {
            throw StorageException("Failed to store original file.")
        }
        val extension: String = FileUploadUtil.getExtensionByStringHandling(file.originalFilename!!);
        println(":::::$extension")
        println(":::::${file.name}")
        println(":::::${file.originalFilename}")
        val filename: String = "${UUID.randomUUID()}.$extension"

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }

        try {
            val inputStream: InputStream = file.inputStream
            val filePath: Path = uploadPath!!.resolve(filename)
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)

            return filename
        } catch (ioe: IOException) {
            throw IOException("Could not save file: $filename", ioe)
        }

    }

    override fun loadAll(): Stream<Path?> {
        try {
            return Files.walk(uploadPath, 1)
                .filter { path: Path -> path != uploadPath }
                .map<Path?>(uploadPath!!::relativize)
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    override fun load(filename: String): Path? {
        return uploadPath!!.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file: Path? = load(filename)
            if (file == null || !Files.exists(file)) {
                throw StorageFileNotFoundException("File not found: $filename")
            }
            val resource: Resource = UrlResource(file.toUri())
            if (resource.exists() || resource.isReadable) {
                return resource
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(uploadPath.toFile())
    }

    override fun delete(filename: String) {
        Files.deleteIfExists(uploadPath.resolve(filename))
    }
}