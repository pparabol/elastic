package ru.test.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.test.elastic.model.Student;

@Repository
public interface StudentRepository extends ElasticsearchRepository<Student, Long> {

}
