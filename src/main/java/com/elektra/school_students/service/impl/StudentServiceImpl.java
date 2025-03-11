package com.elektra.school_students.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elektra.school_students.dao.StudentDao;
import com.elektra.school_students.mapper.StudentMapper;
import com.elektra.school_students.request.StudentRequestPost;
import com.elektra.school_students.request.StudentRequestPut;
import com.elektra.school_students.response.StudentResponse;
import com.elektra.school_students.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{
    private final StudentMapper studentMapper;
    private final StudentDao studentDao;

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentMapper.entityToResponseList(studentDao.getAllStudents());
    }

    @Override
    public List<StudentResponse> filterStudents(String filterType, String filterValue) {
        return studentMapper.entityToResponseList(studentDao.filerStudents(filterType, filterValue));
    }

    @Override
    public StudentResponse getStudentByUuid(String uuid) {
        return studentMapper.entityToResponse(studentDao.getStudentByUuid(uuid));
    }

    @Override
    public StudentResponse updateStudent(String uuid, StudentRequestPut studentRequestPut) {
        return studentMapper.entityToResponse(studentDao.updateStudent(uuid, studentRequestPut));
    }

    @Override
    public StudentResponse addStudent(StudentRequestPost studentRequestPost) {
        return studentMapper.entityToResponse(studentDao.addStudent(studentRequestPost));
    }
}
