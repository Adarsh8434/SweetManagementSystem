package com.sweetshop.sweetshop_management.service;

import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.exception.UsernameAlreadyExistsException;
import com.sweetshop.sweetshop_management.modal.Role;
import com.sweetshop.sweetshop_management.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
        private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        validateUsername(user.getUsername());
     user.setPassword(passwordEncoder.encode(user.getPassword()));

   user.setRole(Role.USER); // Default role is USER
        return userRepository.save(user);
    }

    private void validateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
    }
}
