package ru.test.elastic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.elastic.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
