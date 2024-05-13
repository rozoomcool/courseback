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
class MediaController(
    private val mediaFileService: MediaFileService
) {

    @GetMapping
    fun get(@RequestParam filename: String): Mono<ResponseEntity<Resource>> {
        val resource = mediaFileService.read(filename)
        val headers: HttpHeaders = HttpHeaders()
        headers.contentType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM)
        return Mono.fromSupplier {ResponseEntity.ok().headers(headers).body(resource)}
    }

    @PostMapping
    fun post(@ModelAttribute file: MultipartFile): MediaFile {
        return mediaFileService.save(file)
    }
}