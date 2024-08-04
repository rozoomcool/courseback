//package ru.itabrek.courses.controller
//
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import ru.itabrek.courses.entity.Lecture
//import ru.itabrek.courses.service.CourseProgressService
//import ru.itabrek.courses.service.LectureService
//import java.security.Principal
//
//@RestController
//@RequestMapping("/api/v1/lecture")
//class LectureController(
//        private val lectureService: LectureService,
//        private val progressService: CourseProgressService
//) {
//    @GetMapping("/{id}")
//    fun getById(@PathVariable id: Long, principal: Principal?): ResponseEntity<Lecture> {
//        if (principal != null) {
//            progressService.add()
//        }
//    }
//}