
package com.sweetshop.sweetshop_management.controller;

import com.sweetshop.sweetshop_management.dto.AuthResponse;
import com.sweetshop.sweetshop_management.dto.RegisterRequest;
import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.exception.UsernameAlreadyExistsException;
import com.sweetshop.sweetshop_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
            RegisterRequest request = new RegisterRequest();
    request.setUsername("adarsh");
    request.setPassword("password123");
        User user = new User();
        user.setUsername("adarsh");
        user.setPassword("password123");

        when(userService.registerUser(user)).thenReturn(user);

        ResponseEntity<AuthResponse> response = userController.registerUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("adarsh", response.getBody().getUsername());
    }

    @Test
    void registerUser_DuplicateUsername() {
         RegisterRequest request = new RegisterRequest();
    request.setUsername("adarsh");
    request.setPassword("password123");
        User user = new User();
        user.setUsername("adarsh");

        when(userService.registerUser(user)).thenThrow(new UsernameAlreadyExistsException("Username already exists!"));
        UsernameAlreadyExistsException exception = assertThrows(
            UsernameAlreadyExistsException.class,
            () -> userController.registerUser(request)
        );

        assertEquals("Username already exists!", exception.getMessage());
    }
}

