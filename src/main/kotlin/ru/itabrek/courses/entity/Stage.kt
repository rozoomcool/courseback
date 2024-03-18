package ru.itabrek.courses.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UpdateTimestamp
import ru.itabrek.courses.model.ContentType
import java.util.*

@Entity
@Table(name = "stage")
class Stage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "content_type", nullable = false) @Enumerated(EnumType.STRING)
    val contentType: ContentType,

    @Column(name = "content", nullable = false)
    val content: String,

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    var createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    var updatedAt: Date? = null
) {

}