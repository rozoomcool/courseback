package ru.itabrek.courses.controller

import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.MediaTypeFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import ru.itabrek.courses.entity.MediaFile
import ru.itabrek.courses.service.MediaFileService


@RestController
@RequestMapping("/media")
class MediaFileController(
    private val mediaFileService: MediaFileService
) {

    @GetMapping
    fun getMediaFile(@RequestParam filename: String): Mono<ResponseEntity<Resource>> {
        val resource = mediaFileService.read(filename)
        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM)
        return Mono.fromSupplier {ResponseEntity.ok().headers(headers).body(resource)}
    }

    @PostMapping
    fun postMediaFile(@ModelAttribute file: MultipartFile): MediaFile {
        return mediaFileService.save(file)
    }

    @PutMapping
    fun updateMediaFile(mediaFile: MediaFile, @ModelAttribute file: MultipartFile): MediaFile {
        return mediaFileService.update(mediaFile, file)
    }

    @DeleteMapping
    fun deleteMediaFile(mediaFile: MediaFile): ResponseEntity<Void> {
        try {
            mediaFileService.delete(mediaFile)
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            return ResponseEntity.notFound().build()
        }
    }
}