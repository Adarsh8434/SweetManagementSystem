package com.sweetshop.sweetshop_management.service;


import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }
}

