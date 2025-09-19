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

   assignDefaultRole(user); // Default role is USER
        return userRepository.save(user);
    }

    private void validateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists!");
        }
        
    }
    private void assignDefaultRole(User user) {
        if (user.getRole() == null) { // only assign when not explicitly set
            user.setRole(Role.USER);
        }
    }
    public User login(String username, String password) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Invalid username or password"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new RuntimeException("Invalid username or password");
    }

    return user;
}

}
