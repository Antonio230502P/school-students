package com.elektra.school_students.dao;

import java.util.List;

import com.elektra.school_students.entity.Student;
import com.elektra.school_students.request.StudentRequestPut;

public interface StudentDao {
    List<Student> getAllStudents();

    List<Student> filerStudents(String filterType, String filterValue);

    Student getStudentByUuid(String uuid);

    Student updateStudent(String uuid, StudentRequestPut studentRequestPut);
}
