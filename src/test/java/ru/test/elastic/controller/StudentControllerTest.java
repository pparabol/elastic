package ru.test.elastic.controller;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.test.elastic.model.Student;
import ru.test.elastic.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private Integer port;

    private Student student;
    private String requestBody;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        studentRepository.deleteAll();

        student = Student.builder()
                .name("name")
                .email("email")
                .build();
        requestBody = format("""
                        {
                         "name": "%s",\s
                         "email": "%s"\s
                        }""",
                student.getName(), student.getEmail());
    }

    @Test
    void getStudent() {
        Student savedStudent = studentRepository.save(student);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/students/" + savedStudent.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(student.getName()))
                .body("email", equalTo(student.getEmail()));
    }

    @Test
    void updateStudent() {
        Student savedStudent = studentRepository.save(Student.builder().name("blank").email("blank").build());

        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("/api/v1/students/" + savedStudent.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(student.getName()))
                .body("email", equalTo(student.getEmail()));
    }

    @Test
    void createStudent() {
        given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/api/v1/students")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("name", equalTo(student.getName()))
                .body("email", equalTo(student.getEmail()));
    }

    @Test
    void deleteStudent() {
        Student savedStudent = studentRepository.save(student);

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/students/" + savedStudent.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}