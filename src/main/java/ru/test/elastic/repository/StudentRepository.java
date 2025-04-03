package ru.test.elastic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.elastic.model.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student save(Student student);

    Optional<Student> findById(Long id);

    void deleteById(Long id);
}
