package com.elektra.school_students.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elektra.school_students.response.StudentResponse;
import com.elektra.school_students.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/filter/{filterType}/{filterValue}")
    public ResponseEntity<List<StudentResponse>> getStudentsFiltered(@PathVariable String filterType, @PathVariable String filterValue) {
        return ResponseEntity.ok(studentService.filterStudents(filterType, filterValue));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<StudentResponse> getStudentByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(studentService.getStudentByUuid(uuid));
    }
}
