package ru.test.elastic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.test.elastic.model.Student;
import ru.test.elastic.repository.StudentRepository;

import static java.lang.String.format;
import static reactor.core.publisher.Mono.just;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    public Mono<Student> createStudent(Student student) {
        log.info("Save student: " + student);
        return studentRepository.save(student);
    }

    public Mono<Student> updateStudent(Long id, Student student) {
        log.info(format("Update student by id %d: %s", id, student));
        return just(student).flatMap(st -> {
            st.setId(id);
            return studentRepository.save(student);
        });
    }

    public Mono<Student> getStudent(Long id) {
        log.info("Get student by id " + id);
        return studentRepository.findById(id);
    }

    public Flux<Student> getStudents() {
        log.info("Get all students");
        return studentRepository.findAll();
    }

    public Mono<Void> deleteStudent(Long id) {
        log.info("Delete student by id " + id);
        return studentRepository.deleteById(id);
    }
}
