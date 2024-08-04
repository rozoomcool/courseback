package ru.itabrek.courses.entity

import jakarta.persistence.*

@Entity
class CourseProgress(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var userId: Long,
        var courseId: Long,
        var materialId: Long,
        @Enumerated(value = EnumType.STRING)
        var type: CourseProgressType
) {
}

enum class CourseProgressType {
    LECTURE, TEST
}