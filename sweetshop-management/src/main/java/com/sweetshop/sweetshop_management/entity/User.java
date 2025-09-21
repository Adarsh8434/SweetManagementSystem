package com.sweetshop.sweetshop_management.entity;


import com.sweetshop.sweetshop_management.modal.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER; // "USER" or "ADMIN"

    public void setRole(Role role) {
    this.role = role;
}

    public Role getRole() {
        return role;
    }
}
