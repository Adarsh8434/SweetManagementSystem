package com.sweetshop.sweetshop_management.service;

import com.sweetshop.sweetshop_management.entity.User;
import com.sweetshop.sweetshop_management.exception.UsernameAlreadyExistsException;
import com.sweetshop.sweetshop_management.modal.Role;
import com.sweetshop.sweetshop_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // Mocked dependency

    @InjectMocks
    private UserService userService;        // Class under test

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success_withHashedPassword() {
        // Arrange
        User user = new User();
        user.setUsername("adarsh");
        user.setPassword("password123");

        when(userRepository.findByUsername("adarsh")).thenReturn(Optional.empty());
        // Return the user that service passes in (with hashed password!)
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        User savedUser = userService.registerUser(user);

        // Assert
        assertNotNull(savedUser);
        assertEquals("adarsh", savedUser.getUsername());

        // ✅ Password should be hashed
        assertNotEquals("password123", savedUser.getPassword());
        assertTrue(passwordEncoder.matches("password123", savedUser.getPassword()));

        verify(userRepository, times(1)).findByUsername("adarsh");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_DuplicateUsername() {
        // Arrange
        User user = new User();
        user.setUsername("adarsh");
        user.setPassword("password123");

        when(userRepository.findByUsername("adarsh")).thenReturn(Optional.of(user));

        // Act + Assert
        UsernameAlreadyExistsException exception =
                assertThrows(UsernameAlreadyExistsException.class, () -> userService.registerUser(user));

        assertEquals("Username already exists!", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("adarsh");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_ThrowsException_WhenUsernameAlreadyExists() {
        User existingUser = new User();
        existingUser.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(existingUser));

        assertThrows(UsernameAlreadyExistsException.class,
                () -> userService.registerUser(existingUser));
    }
    @Test
void testRegisterUser_DefaultRoleIsUser() {
    User user = new User();
    user.setUsername("john");
    user.setPassword("secret");

    when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

    User savedUser = userService.registerUser(user);

    assertNotNull(savedUser);
    assertEquals(Role.USER, savedUser.getRole()); // ✅ Default role check
}
@Test
void testRegisterUser_AdminRoleNotAllowedDuringRegistration() {
    User user = new User();
    user.setUsername("adarsh");
    user.setPassword("wanttobecomeadmin");
    user.setRole(Role.ADMIN); 

    when(userRepository.findByUsername("adarsh")).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

    User savedUser = userService.registerUser(user);

    // Should override to USER
    assertEquals(Role.USER, savedUser.getRole(), "Default role should be USER");
}
@Test
void shouldAssignDefaultUserRoleWhenRoleIsNull() {
    User user = createMockUser("newuser", "password123");

    when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

    User savedUser = userService.registerUser(user);

    assertEquals(Role.USER, savedUser.getRole());
}
private User createMockUser(String username, String password) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    return user;
}



}
