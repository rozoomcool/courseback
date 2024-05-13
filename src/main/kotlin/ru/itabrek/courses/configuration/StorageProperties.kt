package ru.itabrek.courses.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("storage")
class StorageProperties {
    @Value("#{upload.path}")
    var location: String = "upload-dir"
}