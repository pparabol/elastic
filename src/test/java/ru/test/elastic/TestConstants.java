package ru.test.elastic;

import ru.test.elastic.model.Student;

public final class TestConstants {
    public static final Student STUDENT_ONE = Student.builder()
            .name("name_one")
            .email("email_one")
            .build();
    public static final Student STUDENT_TWO = Student.builder()
            .name("name_two")
            .email("email_two")
            .build();

    private TestConstants() {

    }
}
