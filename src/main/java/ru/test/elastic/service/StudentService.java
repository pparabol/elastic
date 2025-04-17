package ru.test.elastic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.elastic.exception.NotFoundException;
import ru.test.elastic.model.Student;
import ru.test.elastic.repository.StudentRepository;

import java.io.IOException;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    public Student createStudent(Student student) {
        log.info("Save student: " + student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        log.info(format("Update student by id %d: %s", id, student));
        Student savedStudent = getStudent(id);
        savedStudent.setName(student.getName());
        savedStudent.setEmail(student.getEmail());

        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        log.info("Get student by id " + id);
        return studentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public void deleteStudent(Long id) {
        log.info("Delete student by id " + id);
        getStudent(id);
        studentRepository.deleteById(id);
    }
}
