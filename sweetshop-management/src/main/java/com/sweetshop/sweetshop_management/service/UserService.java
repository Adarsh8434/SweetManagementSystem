package com.sweetshop.sweetshop_management.service;


import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   private final UserRepository userRepository;
   public UserService(UserRepository userRepository) {
       this.userRepository = userRepository;
   }
   

    public User registerUser(User user) {
   if (userRepository.findByUsername(user.getUsername()).isPresent()) {
        throw new IllegalArgumentException("Username already exists!");
    }
    return userRepository.save(user);
}
}

