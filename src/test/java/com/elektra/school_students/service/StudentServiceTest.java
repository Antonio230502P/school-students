package com.elektra.school_students.service;

import com.elektra.school_students.dao.StudentDao;
import com.elektra.school_students.entity.Student;
import com.elektra.school_students.mapper.StudentMapper;
import com.elektra.school_students.request.StudentRequestPost;
import com.elektra.school_students.request.StudentRequestPut;
import com.elektra.school_students.response.StudentResponse;
import com.elektra.school_students.service.impl.StudentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentResponse studentResponse;
    private List<StudentResponse> studentResponseList;
    private String uuid;

    @BeforeEach
    public void setUp() {
        uuid = UUID.randomUUID().toString();
        Timestamp timestamp = Timestamp.from(Instant.now());
        student = new Student(1L, uuid.toString(), "Atenea Martinez", 20, 5, "Ixtapaluca", "Y", "Y", timestamp, timestamp);
        studentResponse = new StudentResponse(uuid.toString(), "Atenea Martinez", 20, 5, "Ixtapaluca", 'Y', 'Y');
        studentResponseList = Collections.singletonList(studentResponse);
    }

    @Test
    public void testGetAllStudents() {
        when(studentDao.getAllStudents()).thenReturn(Collections.emptyList());
        when(studentMapper.entityToResponseList(any())).thenReturn(studentResponseList);

        List<StudentResponse> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(studentDao, times(1)).getAllStudents();
        verify(studentMapper, times(1)).entityToResponseList(any());
    }

    @Test
    public void testGetStudentByUuid() {
        when(studentDao.getStudentByUuid(anyString())).thenReturn(student);
        when(studentMapper.entityToResponse(any())).thenReturn(studentResponse);

        StudentResponse result = studentService.getStudentByUuid("uuid");

        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        verify(studentDao, times(1)).getStudentByUuid(anyString());
        verify(studentMapper, times(1)).entityToResponse(any());
    }

    @Test
    public void testUpdateStudent() {
        StudentRequestPut studentRequestPut = new StudentRequestPut("Atenea Martinez", 20, 5, "Ixtapaluca", "Y");
        when(studentDao.updateStudent(anyString(), any())).thenReturn(student);
        when(studentMapper.entityToResponse(any())).thenReturn(studentResponse);

        StudentResponse result = studentService.updateStudent("uuid", studentRequestPut);

        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        verify(studentDao, times(1)).updateStudent(anyString(), any());
        verify(studentMapper, times(1)).entityToResponse(any());
    }

    @Test
    public void testAddStudent() {
        StudentRequestPost studentRequestPost = new StudentRequestPost("Atenea Martinez", 20, 5, "Ixtapaluca", "Y");
        when(studentDao.addStudent(any())).thenReturn(student);
        when(studentMapper.entityToResponse(any())).thenReturn(studentResponse);

        StudentResponse result = studentService.addStudent(studentRequestPost);

        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
        verify(studentDao, times(1)).addStudent(any());
        verify(studentMapper, times(1)).entityToResponse(any());
    }

    @Test
    public void testUnenrollingStudent() {
        doNothing().when(studentDao).unenrollingStudent(anyString());

        studentService.unenrollingStudent("uuid");

        verify(studentDao, times(1)).unenrollingStudent(anyString());
    }
}
