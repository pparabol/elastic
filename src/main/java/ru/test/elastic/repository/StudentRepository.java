package ru.test.elastic.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.test.elastic.model.Student;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, Long> {

}
