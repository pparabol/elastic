package ru.test.elastic.model;

import lombok.Data;

import java.util.Map;

@Data
public class StudentSearchRequest {
    private Map<String, String> searchFields;
    private Integer offset;
    private Integer limit;
}
