package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        return "login";
    }

}
