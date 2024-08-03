package ru.itabrek.courses.entity

import jakarta.persistence.*

@Entity
@Table(name = "questionVariant")
class QuestionVariant(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var variant: String
)