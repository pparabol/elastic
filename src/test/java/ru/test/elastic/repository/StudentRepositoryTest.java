package ru.test.elastic.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import ru.test.elastic.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = Student.builder()
                .id(1L)
                .name("name")
                .email("email")
                .build();
    }

    @AfterEach
    public void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    public void save() {
        Student savedStudent = studentRepository.save(student).block();

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    @Test
    public void findById() {
        Student savedStudent = studentRepository.save(student).block();

        Student studentById = studentRepository.findById(savedStudent.getId()).block();

        assertThat(studentById).isNotNull();
        assertThat(studentById)
                .hasFieldOrPropertyWithValue("id", savedStudent.getId())
                .hasFieldOrPropertyWithValue("name", savedStudent.getName())
                .hasFieldOrPropertyWithValue("email", savedStudent.getEmail());
    }

    @Test
    public void findAll() {
        studentRepository.save(student);

        Flux<Student> students = studentRepository.findAll();

        assertThat(students).isNotNull();
    }

    @Test
    public void deleteById() {
        Student studentById = studentRepository.save(student)
                .flatMap(savedStudentMono -> studentRepository.deleteById(savedStudentMono.getId()))
                        .then(studentRepository.findById(student.getId())).block();

        assertThat(studentById).isNull();
    }
}
