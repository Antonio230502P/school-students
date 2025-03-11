package com.elektra.school_students.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.elektra.school_students.dao.StudentDao;
import com.elektra.school_students.entity.Student;
import com.elektra.school_students.request.StudentRequestPost;
import com.elektra.school_students.request.StudentRequestPut;

import oracle.jdbc.OracleTypes;

@Repository
public class StudentDaoImpl implements StudentDao {
        private JdbcTemplate jdbcTemplate;
        private SimpleJdbcCall simpleJdbcCall;
        private MapSqlParameterSource mapSqlParameterSource;

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
                                                .activeStudent(((String) row.get("active_student")))
                                                .foreignStudent(((String) row.get("foreign_student")))
                                                .build());
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }

                return students;
        }

        @Override
        public List<Student> filerStudents(String filterType, String filterValue) {
                simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("sp_filter_students")
                                .declareParameters(
                                                new SqlParameter("p_filter_type", OracleTypes.VARCHAR),
                                                new SqlParameter("p_filter_value", OracleTypes.VARCHAR),
                                                new SqlOutParameter("p_result", OracleTypes.REF_CURSOR));

                mapSqlParameterSource = new MapSqlParameterSource()
                                .addValue("p_filter_type", filterType)
                                .addValue("p_filter_value", filterValue);

                Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);

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
                                                .activeStudent(((String) row.get("active_student")))
                                                .foreignStudent(((String) row.get("foreign_student")))
                                                .build());
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }

                return students;
        }

        @Override
        public Student getStudentByUuid(String uuid) {
                simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("sp_get_student_by_uuid")
                                .declareParameters(
                                                new SqlParameter("p_uuid", OracleTypes.VARCHAR),
                                                new SqlOutParameter("p_result", OracleTypes.REF_CURSOR));

                mapSqlParameterSource = new MapSqlParameterSource()
                                .addValue("p_uuid", uuid);

                Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> resultSetList = (List<Map<String, Object>>) result.get("p_result");

                if (resultSetList != null && !resultSetList.isEmpty()) {
                        Map<String, Object> row = resultSetList.get(0);

                        return Student.builder()
                                        .uuid((String) row.get("uuid"))
                                        .name((String) row.get("name"))
                                        .age(((BigDecimal) row.get("age")).intValue())
                                        .grade(((BigDecimal) row.get("grade")).intValue())
                                        .address((String) row.get("address"))
                                        .activeStudent(((String) row.get("active_student")))
                                        .foreignStudent(((String) row.get("foreign_student")))
                                        .build();
                } else {
                        return null;
                }
        }

        @Override
        public Student updateStudent(String uuid, StudentRequestPut studentRequestPut) {
                Student studentToUpdate = getStudentByUuid(uuid);

                simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("sp_update_student")
                                .declareParameters(
                                                new SqlParameter("p_uuid", OracleTypes.VARCHAR),
                                                new SqlParameter("p_name", OracleTypes.VARCHAR),
                                                new SqlParameter("p_age", OracleTypes.NUMBER),
                                                new SqlParameter("p_grade", OracleTypes.NUMBER),
                                                new SqlParameter("p_address", OracleTypes.VARCHAR),
                                                new SqlParameter("p_foreign_student", OracleTypes.CHAR));

                mapSqlParameterSource = new MapSqlParameterSource()
                                .addValue("p_uuid", uuid)
                                .addValue("p_name",
                                                studentRequestPut.getName() != null ? studentRequestPut.getName()
                                                                : studentToUpdate.getName())
                                .addValue("p_age",
                                                studentRequestPut.getAge() != null ? studentRequestPut.getAge()
                                                                : studentToUpdate.getAge())
                                .addValue("p_grade",
                                                studentRequestPut.getGrade() != null ? studentRequestPut.getGrade()
                                                                : studentToUpdate.getGrade())
                                .addValue("p_address",
                                                studentRequestPut.getAddress() != null ? studentRequestPut.getAddress()
                                                                : studentToUpdate.getAddress())
                                .addValue("p_foreign_student",
                                                studentRequestPut.getForeignStudent() != null
                                                                ? studentRequestPut.getForeignStudent()
                                                                : studentToUpdate.getForeignStudent());

                simpleJdbcCall.execute(mapSqlParameterSource);

                return getStudentByUuid(uuid);
        }

        @Override
        public Student addStudent(StudentRequestPost studentRequestPost) {
                simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("sp_insert_student")
                                .declareParameters(
                                                new SqlParameter("p_uuid", OracleTypes.VARCHAR),
                                                new SqlParameter("p_name", OracleTypes.VARCHAR),
                                                new SqlParameter("p_age", OracleTypes.NUMBER),
                                                new SqlParameter("p_grade", OracleTypes.NUMBER),
                                                new SqlParameter("p_address", OracleTypes.VARCHAR),
                                                new SqlParameter("p_foreign_student", OracleTypes.CHAR));

                String uuid = UUID.randomUUID().toString();
                mapSqlParameterSource = new MapSqlParameterSource()
                                .addValue("p_uuid", uuid)
                                .addValue("p_name", studentRequestPost.getName())
                                .addValue("p_name", studentRequestPost.getName())
                                .addValue("p_age", studentRequestPost.getAge())
                                .addValue("p_grade", studentRequestPost.getGrade())
                                .addValue("p_address", studentRequestPost.getAddress())
                                .addValue("p_foreign_student", studentRequestPost.getForeignStudent());

                simpleJdbcCall.execute(mapSqlParameterSource);

                return getStudentByUuid(uuid);
        }

        @Override
        public void unenrollingStudent(String uuid) {
                simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("sp_unenrolling_student")
                                .declareParameters(new SqlParameter("p_uuid", OracleTypes.VARCHAR));
                
                mapSqlParameterSource = new MapSqlParameterSource().addValue("p_uuid", uuid);

                simpleJdbcCall.execute(mapSqlParameterSource);
        }
}
