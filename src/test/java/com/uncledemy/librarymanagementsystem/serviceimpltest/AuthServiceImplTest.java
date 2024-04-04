package com.uncledemy.librarymanagementsystem.serviceimpltest;

import com.uncledemy.librarymanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.librarymanagementsystem.dto.LoginDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.UserNotFoundException;
import com.uncledemy.librarymanagementsystem.model.User;
import com.uncledemy.librarymanagementsystem.repository.UserRepository;
import com.uncledemy.librarymanagementsystem.security.JwtService;
import com.uncledemy.librarymanagementsystem.security.config.SecureUser;
import com.uncledemy.librarymanagementsystem.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_WithValidCredentials_ShouldReturnAuthenticationResponse() throws UserNotFoundException, InvalidPasswordException {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password");

        User user = new User();
        user.setId(1L); // Set an id for the user
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));
        when(jwtService.generateToken(any(SecureUser.class))).thenReturn("jwtToken");

        // Act
        AuthenticationResponse response = authService.login(loginDto);

        // Assert
        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals(1, response.getUserId());
    }



    @Test
    void login_WithInvalidCredentials_ShouldThrowInvalidPasswordException() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("wrongPassword");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        assertThrows(InvalidPasswordException.class, () -> authService.login(loginDto));
    }

    @Test
    void login_WithNonExistentUser_ShouldThrowUserNotFoundException() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("nonExistentUser@example.com");
        loginDto.setPassword("password");

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> authService.login(loginDto));
    }

}
