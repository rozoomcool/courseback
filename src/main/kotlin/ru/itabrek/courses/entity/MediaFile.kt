package ru.itabrek.courses.entity

import jakarta.persistence.*

@Entity
@Table(name = "media-file")
class MediaFile(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @Column(name = "file_name", nullable = false) var fileName: String
) {
}