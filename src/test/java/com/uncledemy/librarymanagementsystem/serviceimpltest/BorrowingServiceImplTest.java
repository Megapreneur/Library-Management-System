package com.uncledemy.librarymanagementsystem.serviceimpltest;

import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.model.BorrowingRecord;
import com.uncledemy.librarymanagementsystem.model.Patron;
import com.uncledemy.librarymanagementsystem.repository.BookRepository;
import com.uncledemy.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.uncledemy.librarymanagementsystem.repository.PatronRepository;
import com.uncledemy.librarymanagementsystem.service.BorrowingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BorrowingServiceImplTest {


    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private BorrowingServiceImpl borrowingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void borrowBook_WithAvailableBook_ShouldBorrowBook() throws LibraryManagementException {
        // Arrange
        long bookId = 1;
        long patronId = 1;
        Book book = new Book();
        book.setAvailableForBorrowing(true);
        Patron patron = new Patron();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        // Act
        borrowingService.borrowBook(bookId, patronId);

        // Assert
        assertFalse(book.isAvailableForBorrowing());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void borrowBook_WithUnavailableBook_ShouldThrowLibraryManagementException() {
        // Arrange
        long bookId = 1;
        long patronId = 1;
        Book book = new Book();
        book.setAvailableForBorrowing(false);
        Patron patron = new Patron();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));

        // Act & Assert
        assertThrows(LibraryManagementException.class, () -> borrowingService.borrowBook(bookId, patronId));
        assertFalse(book.isAvailableForBorrowing()); // Ensure book state is not changed
        verify(borrowingRecordRepository, never()).save(any());
        verify(bookRepository, never()).save(book); // Ensure book is not saved
    }

    @Test
    void returnBook_WithValidBorrowingRecord_ShouldReturnBookAndUpdateBorrowingRecord() throws LibraryManagementException {
        // Arrange
        long bookId = 1;
        long patronId = 1;
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(new Book());
        borrowingRecord.setPatron(new Patron());
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Act
        borrowingService.returnBook(bookId, patronId);

        // Assert
        assertNotNull(borrowingRecord.getReturnDate());
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
        assertTrue(borrowingRecord.getBook().isAvailableForBorrowing());
        verify(bookRepository, times(1)).save(borrowingRecord.getBook());
    }

    @Test
    void returnBook_WithInvalidBorrowingRecord_ShouldThrowLibraryManagementException() {
        // Arrange
        long bookId = 1;
        long patronId = 1;
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(anyLong(), anyLong())).thenReturn(null);

        // Act & Assert
        assertThrows(LibraryManagementException.class, () -> borrowingService.returnBook(bookId, patronId));
        verify(borrowingRecordRepository, never()).save(any(BorrowingRecord.class));
        verify(bookRepository, never()).save(any(Book.class));
    }





}
