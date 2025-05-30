package com.project.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

    @GetMapping("/unauthorized")
    public String unauthorizedPage() {
        return "unauthorized";
    }
}
