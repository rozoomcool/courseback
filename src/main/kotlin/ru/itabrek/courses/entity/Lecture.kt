package ru.itabrek.courses.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import ru.itabrek.courses.model.ContentType
import java.util.*

@Entity
@Table(name = "lecture")
class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    var content: String,

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    var createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    var updatedAt: Date? = null
) {

}