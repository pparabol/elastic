package ru.test.elastic.service;

import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Service;
import ru.test.elastic.model.Student;
import ru.test.elastic.model.StudentSearchRequest;
import ru.test.elastic.repository.StudentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentService {
    private static final String STUDENT_INDEX = "students";

    private final StudentRepository studentRepository;
    private final OpenSearchClient openSearchClient;

    public Student createStudent(Student student) throws IOException {
        // Save to Database
        Student savedStudent = studentRepository.save(student);

        // Save to OpenSearch
        openSearchClient.index(i -> i.index(STUDENT_INDEX).id(savedStudent.getId().toString()).document(savedStudent));

        return savedStudent;
    }

    public Student updateStudent(Long id, Student student) throws IOException {
        Student existingStudent = getStudent(id);
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());

        // Update in Database
        Student updatedStudent = studentRepository.save(existingStudent);

        // Update in OpenSearch
        openSearchClient.index(i -> i.index(STUDENT_INDEX).id(String.valueOf(id)).document(updatedStudent));

        return updatedStudent;
    }

    public Student getStudent(Long id) {
        // Get from Database
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void deleteStudent(Long id) throws IOException {
        // Delete from OpenSearch
        openSearchClient.delete(d -> d.index(STUDENT_INDEX).id(String.valueOf(id)));

        // Delete from Database
        studentRepository.deleteById(id);
    }

    public List<Student> searchStudents(StudentSearchRequest searchRequest) throws IOException {
        // Build the search query
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // Add search conditions for each field
        if (searchRequest.getSearchFields() != null) {
            for (Map.Entry<String, String> field : searchRequest.getSearchFields().entrySet()) {
                boolQueryBuilder.must(Query.of(q -> q
                        .match(m -> m
                                .field(field.getKey())
                                .query(FieldValue.of(field.getValue()))
                        )
                ));
            }
        }

        // Build the search request with pagination
        SearchRequest request = new SearchRequest.Builder()
                .index(STUDENT_INDEX)
                .query(boolQueryBuilder.build()._toQuery())
                .from(searchRequest.getOffset())
                .size(searchRequest.getLimit())
                .build();

        // Execute search
        SearchResponse<Student> searchResponse = openSearchClient.search(request, Student.class);

        // Convert response to list
        List<Student> students = new ArrayList<>();
        searchResponse.hits().hits().forEach(hit -> students.add(hit.source()));
        return students;
    }
}
