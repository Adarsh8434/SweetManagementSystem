package com.sweetshop.sweetshop_management.service;

import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // Mocked dependency

    @InjectMocks
    private UserService userService;        // Class under test

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        User user = new User();
        user.setUsername("adarsh");
        user.setPassword("password123");

        // Repo says username doesn't exist
        when(userRepository.findByUsername("adarsh")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User savedUser = userService.registerUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("adarsh", savedUser.getUsername());
        verify(userRepository, times(1)).findByUsername("adarsh");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_DuplicateUsername() {
        // Arrange
        User user = new User();
        user.setUsername("adarsh");
        user.setPassword("password123");

        // Repo says username already exists
        when(userRepository.findByUsername("adarsh")).thenReturn(Optional.of(user));

        // Act + Assert
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));

        assertEquals("Username already exists!", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("adarsh");
        verify(userRepository, never()).save(any(User.class)); // Should not save duplicate
    }
}
