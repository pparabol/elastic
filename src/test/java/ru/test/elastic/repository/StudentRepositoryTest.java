package ru.test.elastic.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.test.elastic.model.Student;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static ru.test.elastic.TestConstants.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    public void tearDown() {
        studentRepository.delete(TEST_STUDENT);
    }

    @Test
    public void save() {
        Student savedStudent = studentRepository.save(TEST_STUDENT);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    @Test
    public void findById() {
        Student savedStudent = studentRepository.save(TEST_STUDENT);

        Optional<Student> studentById = studentRepository.findById(savedStudent.getId());

        assertThat(studentById).isPresent();
        assertThat(studentById.get())
                .hasFieldOrPropertyWithValue("id", savedStudent.getId())
                .hasFieldOrPropertyWithValue("name", savedStudent.getName())
                .hasFieldOrPropertyWithValue("email", savedStudent.getEmail());
    }

    @Test
    public void findAll() {
        studentRepository.save(TEST_STUDENT);

        List<Student> students = studentRepository.findAll();

        assertThat(students).isNotNull();
        assertThat(students).hasSize(1);
    }

    @Test
    public void deleteById() {
        Student savedStudent = studentRepository.save(TEST_STUDENT);

        studentRepository.deleteById(savedStudent.getId());
        Optional<Student> studentById = studentRepository.findById(savedStudent.getId());

        assertThat(studentById).isEmpty();
    }
}
