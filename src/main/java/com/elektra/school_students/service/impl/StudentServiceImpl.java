package com.elektra.school_students.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elektra.school_students.dao.StudentDao;
import com.elektra.school_students.mapper.StudentMapper;
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
}
