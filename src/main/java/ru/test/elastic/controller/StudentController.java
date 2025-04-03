package ru.test.elastic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test.elastic.model.Student;
import ru.test.elastic.model.StudentSearchRequest;
import ru.test.elastic.service.StudentService;

import java.io.IOException;
import java.util.List;

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
            return ResponseEntity.ok(updatedStudent);
        } catch (IOException e) {
            log.error("Error updating student", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestBody StudentSearchRequest searchRequest) {
        try {
            List<Student> students = studentService.searchStudents(searchRequest);
            return ResponseEntity.ok(students);
        } catch (IOException e) {
            log.error("Error searching students", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
