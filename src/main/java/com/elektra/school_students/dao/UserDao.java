package com.elektra.school_students.dao;

import com.elektra.school_students.entity.User;

public interface UserDao {
    User findByEmail(String email);
}
