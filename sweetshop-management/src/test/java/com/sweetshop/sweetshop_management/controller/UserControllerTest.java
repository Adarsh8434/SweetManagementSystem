package com.sweetshop.sweetshop_management.controller;

import com.sweetshop.sweetshop_management.dto.AuthResponse;
import com.sweetshop.sweetshop_management.dto.RegisterRequest;
import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.exception.UsernameAlreadyExistsException;
import com.sweetshop.sweetshop_management.modal.Role;
import com.sweetshop.sweetshop_management.security.JwtUtil;
import com.sweetshop.sweetshop_management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    // ------------------ REGISTER ------------------

    @Test
    void registerUser_Success() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("adarsh");
        request.setPassword("password123");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("adarsh");
        savedUser.setPassword("hashed");
        savedUser.setRole(Role.USER);

        when(userService.registerUser(any(User.class))).thenReturn(savedUser);

        // Act
        ResponseEntity<AuthResponse> response = userController.registerUser(request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("adarsh", response.getBody().getUsername());
        assertEquals("USER", response.getBody().getRole());
        assertNull(response.getBody().getToken()); // no token at register
    }

    @Test
    void registerUser_DuplicateUsername() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("adarsh");
        request.setPassword("password123");

        when(userService.registerUser(any(User.class)))
                .thenThrow(new UsernameAlreadyExistsException("Username already exists!"));

        // Act + Assert
        UsernameAlreadyExistsException exception = assertThrows(
                UsernameAlreadyExistsException.class,
                () -> userController.registerUser(request)
        );

        assertEquals("Username already exists!", exception.getMessage());
    }

    // ------------------ LOGIN ------------------

    @Test
    void login_Success_ReturnsAuthResponse() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("adarsh");
        request.setPassword("password123");

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("adarsh");
        mockUser.setPassword("hashed");
        mockUser.setRole(Role.USER);

        when(userService.login("adarsh", "password123")).thenReturn(mockUser);

        String fakeToken = "fake-jwt-token";

        try (MockedStatic<JwtUtil> mockedJwt = mockStatic(JwtUtil.class)) {
            mockedJwt.when(() -> JwtUtil.generateToken("adarsh", "USER"))
                     .thenReturn(fakeToken);

            // Act
            ResponseEntity<AuthResponse> response = userController.login(request);

            // Assert
            assertNotNull(response);
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("adarsh", response.getBody().getUsername());
            assertEquals("USER", response.getBody().getRole());
            assertEquals(fakeToken, response.getBody().getToken());
        }
    }
    @Test
void login_InvalidCredentials_ReturnsUnauthorized() {
    RegisterRequest request = new RegisterRequest();
    request.setUsername("adarsh");
    request.setPassword("wrongpass");

    when(userService.login("adarsh", "wrongpass"))
            .thenThrow(new RuntimeException("Invalid username or password"));

    RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> userController.login(request)
    );

    assertEquals("Invalid username or password", exception.getMessage());
}

}

