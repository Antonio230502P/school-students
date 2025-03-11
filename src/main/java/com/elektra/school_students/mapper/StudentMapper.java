package com.elektra.school_students.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.elektra.school_students.entity.Student;
import com.elektra.school_students.request.StudentRequestPost;
import com.elektra.school_students.request.StudentRequestPut;
import com.elektra.school_students.response.StudentResponse;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentResponse entityToResponse(Student student);

    List<StudentResponse> entityToResponseList(List<Student> students);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activeStudent", ignore = true)
    Student requestToEntity(StudentRequestPost studentRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activeStudent", ignore = true)
    Student requestToEntity(StudentRequestPut studentRequest);
}
