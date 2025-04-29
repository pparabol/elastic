package ru.test.elastic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.test.elastic.model.Student;
import ru.test.elastic.service.StudentService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Mono<Student>> createStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<Student>> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.updateStudent(id, student));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Student>> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @GetMapping
    public ResponseEntity<Flux<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(studentService.deleteStudent(id));
    }
}
