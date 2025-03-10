package com.elektra.school_students.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private String name;

    private Integer age;

    private Integer grade;

    private String address;
    
    private Character foreignStudent;
}
