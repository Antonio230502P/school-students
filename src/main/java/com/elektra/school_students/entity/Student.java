package com.elektra.school_students.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")

@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "active_student", nullable = false)
    private String activeStudent;

    @Column(name = "foreign_student", nullable = false)
    private String foreignStudent;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
}
