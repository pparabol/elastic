package ru.test.elastic.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.test.elastic.model.Student;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static ru.test.elastic.TestConstants.STUDENT_ONE;
import static ru.test.elastic.TestConstants.STUDENT_TWO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void save() {
        Student savedStudent = studentRepository.save(STUDENT_ONE);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    @Test
    public void findById() {
        Student savedStudent = studentRepository.save(STUDENT_ONE);

        Optional<Student> studentById = studentRepository.findById(savedStudent.getId());

        assertThat(studentById).isPresent();
        assertThat(studentById.get())
                .hasFieldOrPropertyWithValue("id", savedStudent.getId())
                .hasFieldOrPropertyWithValue("name", savedStudent.getName())
                .hasFieldOrPropertyWithValue("email", savedStudent.getEmail());
    }

    @Test
    public void findAll() {
        studentRepository.save(STUDENT_ONE);
        studentRepository.save(STUDENT_TWO);

        List<Student> students = studentRepository.findAll();

        assertThat(students).isNotNull();
        assertThat(students).hasSize(2);
    }

    @Test
    public void deleteById() {
        Student savedStudent = studentRepository.save(STUDENT_ONE);

        studentRepository.deleteById(savedStudent.getId());
        Optional<Student> studentById = studentRepository.findById(savedStudent.getId());

        assertThat(studentById).isEmpty();
    }
}
