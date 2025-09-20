package com.sweetshop.sweetshop_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.service.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/promote/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> promoteToAdmin(@PathVariable String username) {
        User promoted = userService.promoteToAdmin(username);
        return ResponseEntity.ok(promoted);
    }
}
