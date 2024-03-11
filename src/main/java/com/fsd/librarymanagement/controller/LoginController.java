package com.fsd.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /* View */
    // Login form
    @GetMapping("/showLoginPage")
    public String showLoginPage() {
        return "login/login-form";
    }

    // Access Denied Page
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "login/access-denied";
    }

}
