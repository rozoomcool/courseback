package ru.itabrek.courses.entity

import jakarta.persistence.*
import org.apache.commons.lang3.mutable.Mutable
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
    var title: String,

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    var description: String,

    @ManyToOne(targetEntity = User::class)
    @JoinColumn(name = "owner_id", nullable = false)
    var owner: User? = null,

    @OneToMany(cascade = [CascadeType.ALL])
//    @JoinColumn(name = "course_id", nullable = false)
    var chapters: MutableSet<Chapter> = mutableSetOf(),

    @Column(name = "complexity", nullable = false) @Enumerated(EnumType.STRING)
    var complexity: Complexity,

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    val createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    val updatedAt: Date? = null
)