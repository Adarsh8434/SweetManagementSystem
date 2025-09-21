package com.sweetshop.sweetshop_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private Long id;
    private String username;
    private String role;
    private String token;

    public AuthResponse(Long id, String username, String role, String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }
}
