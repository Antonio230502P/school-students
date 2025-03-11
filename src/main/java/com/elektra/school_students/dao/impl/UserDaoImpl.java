package com.elektra.school_students.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.elektra.school_students.dao.UserDao;
import com.elektra.school_students.entity.User;

import oracle.jdbc.OracleTypes;

@Repository
public class UserDaoImpl implements UserDao {
    private String sql;
    private MapSqlParameterSource params;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User findByEmail(String email) {
        sql = "SELECT * FROM C##SCHOOL.USERS WHERE EMAIL = :email";

        params = new MapSqlParameterSource();
        params.addValue("email", email, OracleTypes.VARCHAR);

        try {
            return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            return null;
        }
    }

}
