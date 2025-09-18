package com.sweetshop.sweetshop_management.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sweets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Sweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    private double price;

    private int quantity;
}

