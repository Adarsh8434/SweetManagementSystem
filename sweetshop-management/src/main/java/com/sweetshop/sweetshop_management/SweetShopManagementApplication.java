package com.sweetshop.sweetshop_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.modal.Role;
import com.sweetshop.sweetshop_management.repository.UserRepository;

@SpringBootApplication
public class SweetShopManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SweetShopManagementApplication.class, args);
	}
	@Bean
	  CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin user created: username=admin, password=admin123");
            }
        };
	}
}
