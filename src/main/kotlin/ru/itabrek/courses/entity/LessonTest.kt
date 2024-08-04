package ru.itabrek.courses.entity

import jakarta.persistence.*

@Entity
@Table(name = "lessonTest")
class LessonTest (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    var question: String,
    @OneToMany(cascade = [CascadeType.ALL])
    var variants: Set<QuestionVariant> = mutableSetOf()
)