package com.elektra.school_students.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elektra.school_students.request.StudentRequestPost;
import com.elektra.school_students.request.StudentRequestPut;
import com.elektra.school_students.response.StudentResponse;
import com.elektra.school_students.service.StudentService;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<StudentResponse>> getStudentsFiltered(@PathVariable String filterType,
            @PathVariable String filterValue) {
        return ResponseEntity.ok(studentService.filterStudents(filterType, filterValue));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<StudentResponse> getStudentByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(studentService.getStudentByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(@Valid @RequestBody StudentRequestPost studentRequestPost) {
        return new ResponseEntity<>(studentService.addStudent(studentRequestPost), CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable String uuid,
            @Valid @RequestBody StudentRequestPut studentRequestPut) {
        return ResponseEntity.ok(studentService.updateStudent(uuid, studentRequestPut));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<StudentResponse> unenrollingStudent(@PathVariable String uuid) {
        studentService.unenrollingStudent(uuid);
        return ResponseEntity.noContent().build();
    }
}
