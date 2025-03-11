package com.elektra.school_students.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.elektra.school_students.request.StudentRequestPost;
import com.elektra.school_students.request.StudentRequestPut;
import com.elektra.school_students.response.StudentResponse;
import com.elektra.school_students.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() throws Exception {
        List<StudentResponse> students = new ArrayList<>();
        students.add(
                new StudentResponse(UUID.randomUUID().toString(), "Atenea Martinez", 20, 5, "Ixtapaluca", 'Y', 'Y'));
        students.add(new StudentResponse(UUID.randomUUID().toString(), "Luis Enrique Rodríguez Rojas", 21, 6,
                "Iztapalapa", 'Y', 'N'));
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Atenea Martinez"))
                .andExpect(jsonPath("$[1].name").value("Luis Enrique Rodríguez Rojas"));
    }

    @Test
    void getStudentsFiltered_ShouldReturnFilteredStudents() throws Exception {
        String filterType = "name";
        String filterValue = "atenea";
        List<StudentResponse> filteredStudents = new ArrayList<>();
        filteredStudents.add(
                new StudentResponse(UUID.randomUUID().toString(), "Atenea Martinez", 20, 5, "Ixtapaluca", 'Y', 'Y'));
        when(studentService.filterStudents(filterType, filterValue)).thenReturn(filteredStudents);

        mockMvc.perform(get("/students/filter/{filterType}/{filterValue}", filterType, filterValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Atenea Martinez"));
    }

    @Test
    void getStudentByUuid_ShouldReturnStudent() throws Exception {
        String uuid = UUID.randomUUID().toString();
        StudentResponse student = new StudentResponse(UUID.randomUUID().toString(), "Atenea Martinez", 20, 5,
                "Ixtapaluca", 'Y', 'Y');
        when(studentService.getStudentByUuid(uuid.toString())).thenReturn(student);

        mockMvc.perform(get("/students/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Atenea Martinez"));
    }

    @Test
    void addStudent_ShouldReturnCreatedStudent() throws Exception {
        StudentRequestPost request = new StudentRequestPost("Atenea Martinez", 20, 5, "Ixtapaluca", "Y");
        StudentResponse response = new StudentResponse(UUID.randomUUID().toString(), "Atenea Martinez", 20, 5,
                "Ixtapaluca", 'Y', 'Y');

        when(studentService.addStudent(any(StudentRequestPost.class))).thenReturn(response);

        mockMvc.perform(post("/students")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Atenea Martinez"));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() throws Exception {
        UUID uuid = UUID.randomUUID();
        StudentRequestPut request = new StudentRequestPut("Atenea Martinez", 20, 5, "Ixtapaluca", "Y");
        StudentResponse response = new StudentResponse(UUID.randomUUID().toString(), "Atenea Martinez", 20, 5,
                "Ixtapaluca", 'Y', 'Y');
        when(studentService.updateStudent(eq(uuid.toString()), any(StudentRequestPut.class))).thenReturn(response);

        mockMvc.perform(put("/students/{uuid}", uuid)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Atenea Martinez"));
    }

    @Test
    void unenrollingStudent_ShouldReturnNoContent() throws Exception {
        UUID uuid = UUID.randomUUID();
        doNothing().when(studentService).unenrollingStudent(uuid.toString());

        mockMvc.perform(delete("/students/{uuid}", uuid))
                .andExpect(status().isNoContent());
    }
}