package ru.test.elastic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test.elastic.model.Student;
import ru.test.elastic.service.StudentService;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            log.info("Created student: " + student);
            return ResponseEntity.ok(createdStudent);
        } catch (IOException e) {
            log.error("Error creating student", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student existingStudent = studentService.getStudent(id);
        if (existingStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            log.info("Updated student: " + student);
            return ResponseEntity.ok(updatedStudent);
        } catch (IOException e) {
            log.error("Error updating student", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student existingStudent = studentService.getStudent(id);
        return existingStudent == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(existingStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
