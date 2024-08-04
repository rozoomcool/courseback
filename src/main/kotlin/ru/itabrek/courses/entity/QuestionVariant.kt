package ru.itabrek.courses.entity

import jakarta.persistence.*

@Entity
@Table(name = "questionVariant")
class QuestionVariant(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "variant", nullable = false, columnDefinition = "TEXT")
    var variant: String,
    var isAnswer: Boolean
)