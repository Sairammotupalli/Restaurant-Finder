package com.project.user.controller;

import com.project.user.entity.User;
import com.project.user.entity.UserDetails;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("user") User user) {
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Create and associate UserDetails
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName("Default"); // Placeholder values
        userDetails.setLastName("User");
        userDetails.setUser(user); // Set the user in UserDetails
        user.setUserDetails(userDetails); // Set the UserDetails in User

        userService.saveUser(user); // Save User along with UserDetails
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
