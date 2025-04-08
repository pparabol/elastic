package ru.test.elastic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.elastic.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
