package ru.test.elastic;

import ru.test.elastic.model.Student;

public final class TestConstants {
    public static final Student TEST_STUDENT = Student.builder()
            .name("name")
            .email("email")
            .build();

    private TestConstants() {

    }
}
