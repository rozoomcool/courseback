package ru.itabrek.courses.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.itabrek.courses.entity.Course

@Repository
interface CourseRepository : CrudRepository<Course, Long> {
}