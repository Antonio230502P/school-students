package com.elektra.school_students.service;

import java.util.List;

import com.elektra.school_students.response.StudentResponse;

public interface StudentService {
    List<StudentResponse> getAllStudents();
}
