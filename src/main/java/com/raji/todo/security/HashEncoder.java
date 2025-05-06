package com.raji.todo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HashEncoder {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean matches(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }

}
