package ru.itabrek.courses.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.itabrek.courses.entity.Lecture

@Repository
interface LectureRepository : CrudRepository<Lecture, Long> {
}