package com.elektra.school_students.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCredentials {
    private String email;
    private String password;   
}
