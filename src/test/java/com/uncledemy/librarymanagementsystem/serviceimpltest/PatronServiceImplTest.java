package com.uncledemy.librarymanagementsystem.serviceimpltest;

import com.uncledemy.librarymanagementsystem.dto.PatronDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.model.Patron;
import com.uncledemy.librarymanagementsystem.repository.PatronRepository;
import com.uncledemy.librarymanagementsystem.service.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PatronServiceImplTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewPatron_WithValidPatronDto_ShouldAddNewPatron() throws UserAlreadyExistException, InvalidEmailException, InvalidPhoneNumberException {
        // Arrange
        PatronDto patronDto = new PatronDto("John", "Doe", "+1234567890", "john1@example.com", "123 Street");

        when(patronRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(patronRepository.save(any())).thenReturn(new Patron());

        // Act
        boolean result = patronService.addNewPatron(patronDto);

        // Assert
        assertTrue(result);
    }

    @Test
    void addNewPatron_WithInvalidEmail_ShouldThrowInvalidEmailException() {
        // Arrange
        PatronDto patronDto = new PatronDto("John", "Doe", "invalidEmail", "+1234567890", "123 Street");

        // Act & Assert
        assertThrows(InvalidEmailException.class, () -> patronService.addNewPatron(patronDto));
    }

    @Test
    void addNewPatron_WithInvalidPhoneNumber_ShouldThrowInvalidPhoneNumberException() {
        // Arrange
        PatronDto patronDto = new PatronDto("John", "Doe", "+7r23fhfrrwqw", "john@example.com", "123 Street");

        // Act & Assert
        assertThrows(InvalidPhoneNumberException.class, () -> patronService.addNewPatron(patronDto));
    }

    @Test
    void addNewPatron_WithExistingEmail_ShouldThrowUserAlreadyExistException() {
        // Arrange
        PatronDto patronDto = new PatronDto("John", "Doe", "+1234567890", "john@example.com", "123 Street");

        when(patronRepository.findByEmail(any())).thenReturn(Optional.of(new Patron()));

        // Act & Assert
        assertThrows(UserAlreadyExistException.class, () -> patronService.addNewPatron(patronDto));
    }

    @Test
    void updateAPatron_WithValidPatronId_ShouldUpdatePatron() throws InvalidEmailException, InvalidPhoneNumberException {
        // Arrange
        long patronId = 1;
        PatronDto patronDto = new PatronDto("John", "Doe", "+1234567890", "john@example.com", "123 Street");

        Patron savedPatron = new Patron();

        when(patronRepository.findById(any())).thenReturn(Optional.of(savedPatron));
        when(patronRepository.save(any())).thenReturn(savedPatron);

        // Act
        boolean result = patronService.updateAPatron(patronId, patronDto);

        // Assert
        assertTrue(result);
    }

    @Test
    void updateAPatron_WithInvalidEmail_ShouldThrowInvalidEmailException() {
        // Arrange
        long patronId = 1;
        PatronDto patronDto = new PatronDto("John", "Doe", "invalidEmail", "+1234567890", "123 Street");

        // Act & Assert
        assertThrows(InvalidEmailException.class, () -> patronService.updateAPatron(patronId, patronDto));
    }

    @Test
    void getPatronById_WithValidPatronId_ShouldReturnPatron() {
        // Arrange
        long patronId = 1;
        Patron expectedPatron = new Patron();

        when(patronRepository.findById(any())).thenReturn(Optional.of(expectedPatron));

        // Act
        Patron actualPatron = patronService.getPatronById(patronId);

        // Assert
        assertEquals(expectedPatron, actualPatron);
    }

    @Test
    void getPatronById_WithInvalidPatronId_ShouldThrowUsernameNotFoundException() {
        // Arrange
        long patronId = 1;

        when(patronRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> patronService.getPatronById(patronId));
    }

    @Test
    void getAllPatrons_ShouldReturnListOfPatrons() {
        // Arrange
        List<Patron> expectedPatrons = new ArrayList<>();
        expectedPatrons.add(new Patron());
        expectedPatrons.add(new Patron());

        when(patronRepository.findAll()).thenReturn(expectedPatrons);

        // Act
        List<Patron> actualPatrons = patronService.getAllPatrons();

        // Assert
        assertEquals(expectedPatrons, actualPatrons);
    }

    @Test
    void deletePatronById_WithValidPatronId_ShouldDeletePatron() {
        // Arrange
        long patronId = 1;

        when(patronRepository.findById(any())).thenReturn(Optional.of(new Patron()));

        // Act
        boolean result = patronService.deletePatronById(patronId);

        // Assert
        assertTrue(result);
    }

    @Test
    void deletePatronById_WithInvalidPatronId_ShouldThrowUsernameNotFoundException() {
        // Arrange
        long patronId = 1;

        when(patronRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> patronService.deletePatronById(patronId));
    }
}
