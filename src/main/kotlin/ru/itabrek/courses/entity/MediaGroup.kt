package ru.itabrek.courses.entity

import jakarta.persistence.*

@Entity
@Table(name = "media_group")
class MediaGroup(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @OneToMany var medias: List<MediaFile>
)