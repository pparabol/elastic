package ru.test.elastic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.test.elastic.model.Student;
import ru.test.elastic.repository.StudentRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student createStudent(Student student) throws IOException {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) throws IOException {
        studentRepository.findById(id)
                .ifPresentOrElse(book1 -> {
                    book1.setName(student.getName());
                    book1.setEmail(student.getEmail());
                },() -> {throw new RuntimeException("Student not found");});

        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElse(null);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
