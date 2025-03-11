package com.elektra.school_students.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.elektra.school_students.dao.StudentDao;
import com.elektra.school_students.entity.Student;

import oracle.jdbc.OracleTypes;

@Repository
public class StudentDaoImpl implements StudentDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Student> getAllStudents() {
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_get_all_students")
                .declareParameters(new SqlOutParameter("p_result", OracleTypes.REF_CURSOR));

        Map<String, Object> result = simpleJdbcCall.execute();

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultSetList = (List<Map<String, Object>>) result.get("p_result");

        List<Student> students = new ArrayList<>();

        try {
            for (Map<String, Object> row : resultSetList) {
                students.add(Student.builder()
                        .uuid((String) row.get("uuid"))
                        .name((String) row.get("name"))
                        .age(((BigDecimal) row.get("age")).intValue())
                        .grade(((BigDecimal) row.get("grade")).intValue())
                        .address((String) row.get("address"))
                        .activeStudent(((String) row.get("active_student")).charAt(0))
                        .foreignStudent(((String) row.get("foreign_student")).charAt(0))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

}