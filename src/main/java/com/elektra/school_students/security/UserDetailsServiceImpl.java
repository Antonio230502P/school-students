package com.elektra.school_students.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.elektra.school_students.dao.UserDao;
import com.elektra.school_students.entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        
        return new UserDetailsImpl(user);
    }

}
