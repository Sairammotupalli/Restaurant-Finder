package com.project.user.controller;

import com.project.user.entity.User;
import com.project.user.entity.UserDetails;
import com.project.user.service.UserDetailsService;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/profile/edit")
    public String showEditProfilePage(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        if (user == null || user.getUserDetails() == null) {
            throw new RuntimeException("User or UserDetails not found for the current user.");
        }

        model.addAttribute("userDetails", user.getUserDetails());
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String handleEditProfile(@ModelAttribute("userDetails") UserDetails userDetails, Principal principal) {
        // Fetch the logged-in user's email
        String email = principal.getName();

        // Fetch the User object
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found for the current session.");
        }

        // Associate User with UserDetails
        userDetails.setUser(user);

        // Save the UserDetails
        userDetailsService.saveUserDetails(userDetails);

        return "redirect:/restaurants";
    }

    @GetMapping("/validate-email")
    public ResponseEntity<Map<String, Boolean>> validateEmail(@RequestParam("email") String email) {
        boolean exists = userService.doesUserExistByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }


}
