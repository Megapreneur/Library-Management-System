package com.uncledemy.librarymanagementsystem.serviceimpltest;

import com.uncledemy.librarymanagementsystem.dto.UserDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.model.User;
import com.uncledemy.librarymanagementsystem.repository.UserRepository;
import com.uncledemy.librarymanagementsystem.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registration_WithValidUserDto_ShouldRegisterUser() throws InvalidPasswordException, UserAlreadyExistException, PasswordMisMatchException, InvalidEmailException, InvalidEmailException {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("tests@example.com");
        userDto.setPassword("Test@123");
        userDto.setConfirmPassword("Test@123");

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.registration(userDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registration_WithExistingUser_ShouldThrowUserAlreadyExistException() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("tests@example.com");
        userDto.setPassword("Test@123");
        userDto.setConfirmPassword("Test@123");

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class, () -> userService.registration(userDto));
    }

    @Test
    void registration_WithMismatchedPassword_ShouldThrowPasswordMisMatchException() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("tests@example.com");
        userDto.setPassword("Password1@");
        userDto.setConfirmPassword("Password2@");

        assertThrows(PasswordMisMatchException.class, () -> userService.registration(userDto));
    }

    @Test
    void registration_WithInvalidPassword_ShouldThrowInvalidPasswordException() {
        UserDto userDto = new UserDto();
        userDto.setPassword("password");

        assertThrows(InvalidPasswordException.class, () -> userService.registration(userDto));
    }

    @Test
    void registration_WithInvalidEmail_ShouldThrowInvalidEmailException() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("test");
        userDto.setPassword("Password2@");
        userDto.setConfirmPassword("Password2@");

        assertThrows(InvalidEmailException.class, () -> userService.registration(userDto));
    }
}
