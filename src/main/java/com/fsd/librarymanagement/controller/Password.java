package com.fsd.librarymanagement.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

    /* Manually Generate hash password */
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "password123"; // replace password
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }
}

