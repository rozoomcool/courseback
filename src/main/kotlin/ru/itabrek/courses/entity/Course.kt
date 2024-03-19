package ru.itabrek.courses.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import ru.itabrek.courses.model.Complexity
import java.util.*

@Entity
@Table(name = "course")
class Course(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val teacher: User? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "course_id", nullable = false)
    val lessons: MutableSet<Lesson>,

    @Column(name = "complexity", nullable = false) @Enumerated(EnumType.STRING)
    val complexity: Complexity,

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    var createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    var updatedAt: Date? = null
) {

}