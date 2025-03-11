package com.elektra.school_students.service;

import java.util.List;

import com.elektra.school_students.request.StudentRequestPost;
import com.elektra.school_students.request.StudentRequestPut;
import com.elektra.school_students.response.StudentResponse;

public interface StudentService {
    List<StudentResponse> getAllStudents();

    List<StudentResponse> filterStudents(String filterType, String filterValue);

    StudentResponse getStudentByUuid(String uuid);

    StudentResponse updateStudent(String uuid, StudentRequestPut studentRequestPut);

    StudentResponse addStudent(StudentRequestPost studentRequestPost);
}
