package com.elektra.school_students.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "students", key = "'all'")
    public List<StudentResponse> getAllStudents() {
        return studentMapper.entityToResponseList(studentDao.getAllStudents());
    }

    @Override
    public List<StudentResponse> filterStudents(String filterType, String filterValue) {
        return studentMapper.entityToResponseList(studentDao.filerStudents(filterType, filterValue));
    }

    @Override
    @Cacheable(value = "student", key = "#uuid")
    public StudentResponse getStudentByUuid(String uuid) {
        return studentMapper.entityToResponse(studentDao.getStudentByUuid(uuid));
    }

    @Override
    @CachePut(cacheNames = "student", key = "#uuid")
    @CacheEvict(cacheNames = "students", key = "'all'", beforeInvocation = true)
    public StudentResponse updateStudent(String uuid, StudentRequestPut studentRequestPut) {
        return studentMapper.entityToResponse(studentDao.updateStudent(uuid, studentRequestPut));
    }

    @Override
    @CacheEvict(cacheNames = "students", key = "'all'", beforeInvocation = true)
    public StudentResponse addStudent(StudentRequestPost studentRequestPost) {
        return studentMapper.entityToResponse(studentDao.addStudent(studentRequestPost));
    }

    @Override
    @CachePut(cacheNames = "student", key = "#uuid")
    @CacheEvict(cacheNames = "students", key = "'all'", beforeInvocation = true)
    public void unenrollingStudent(String uuid) {
        studentDao.unenrollingStudent(uuid);
    }
}
