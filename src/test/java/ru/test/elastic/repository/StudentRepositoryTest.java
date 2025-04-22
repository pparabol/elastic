package ru.test.elastic.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.test.elastic.model.Student;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = Student.builder()
                .name("name")
                .email("email")
                .build();
    }

    @AfterEach
    public void tearDown() {
        studentRepository.delete(student);
    }

    @Test
    public void save() {
        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    @Test
    public void findById() {
        Student savedStudent = studentRepository.save(student);

        Optional<Student> studentById = studentRepository.findById(savedStudent.getId());

        assertThat(studentById).isPresent();
        assertThat(studentById.get())
                .hasFieldOrPropertyWithValue("id", savedStudent.getId())
                .hasFieldOrPropertyWithValue("name", savedStudent.getName())
                .hasFieldOrPropertyWithValue("email", savedStudent.getEmail());
    }

    @Test
    public void findAll() {
        studentRepository.save(student);

        List<Student> students = studentRepository.findAll();

        assertThat(students).isNotNull();
        assertThat(students).hasSize(1);
    }

    @Test
    public void deleteById() {
        Student savedStudent = studentRepository.save(student);

        studentRepository.deleteById(savedStudent.getId());
        Optional<Student> studentById = studentRepository.findById(savedStudent.getId());

        assertThat(studentById).isEmpty();
    }
}
