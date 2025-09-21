package com.sweetshop.sweetshop_management.controller;


import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.security.JwtUtil;
import com.sweetshop.sweetshop_management.service.UserService;
import com.sweetshop.sweetshop_management.dto.AuthResponse;
import com.sweetshop.sweetshop_management.dto.RegisterRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

   @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        User savedUser = userService.registerUser(user);

        // ✅ return safe DTO (no password exposed)
        return ResponseEntity.ok(
                new AuthResponse(
                        savedUser.getId(),
                        savedUser.getUsername(),
                        savedUser.getRole().name(),
                        null // no token on register
                )
        );
    }
@PostMapping("/login")
public ResponseEntity<AuthResponse> login(@RequestBody RegisterRequest request) {
    User user = userService.login(request.getUsername(), request.getPassword());

    String token = JwtUtil.generateToken(user.getUsername(), user.getRole().name());

    return ResponseEntity.ok(
            new AuthResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getRole().name(),
                    token
            )
    );
}

}

