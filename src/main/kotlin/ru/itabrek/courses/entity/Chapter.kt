package ru.itabrek.courses.entity

import jakarta.persistence.*

@Entity
@Table(name = "chapter")
class Chapter(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,

    @OneToMany(cascade = [CascadeType.ALL])
    var lessons: MutableList<Lesson> = mutableListOf(),
)