package ru.itabrek.courses.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
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
//    @JoinColumn(name = "stage_id", nullable = false)
    var lectures: MutableSet<Lecture> = mutableSetOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    var tests: MutableSet<LessonTest> = mutableSetOf(),

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    val createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    val updatedAt: Date? = null
) {
}