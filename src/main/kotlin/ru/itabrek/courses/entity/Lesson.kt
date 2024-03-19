package ru.itabrek.courses.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "lesson")
class Lesson(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    var title: String,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "stage_id", nullable = false)
    var stages: MutableSet<Stage>,

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    val createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    val updatedAt: Date? = null
) {
}