
package com.sweetshop.sweetshop_management.service;

import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository; 

    @InjectMocks
    private UserService userService;        // Class under test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange (create input user)
        User user = new User();
        user.setUsername("adarsh");
        user.setPassword("password123");

        // Define repo behavior
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.registerUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("adarsh", savedUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }
}

