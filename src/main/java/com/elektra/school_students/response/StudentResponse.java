package com.elektra.school_students.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {
    private String uuid;

    private String name;

    private Integer age;

    private Integer grade;

    private String address;

    private Character activeStudent;

    private Character foreignStudent;
}
