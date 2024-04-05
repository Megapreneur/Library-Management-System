package com.uncledemy.librarymanagementsystem.serviceimpltest;

import com.uncledemy.librarymanagementsystem.dto.BookDto;
import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.repository.BookRepository;
import com.uncledemy.librarymanagementsystem.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewBook_WithNonExistingISBN_ShouldAddNewBook() throws LibraryManagementException {
        BookDto bookDto = new BookDto("Title", "Author", "1234567890", "2022");

        when(bookRepository.findByIsbn(any())).thenReturn(Optional.empty());
        when(bookRepository.save(any())).thenReturn(new Book());

        boolean result = bookService.addNewBook(bookDto);

        assertTrue(result);
    }

    @Test
    void addNewBook_WithExistingISBN_ShouldThrowLibraryManagementException() {
        BookDto bookDto = new BookDto("Title", "Author", "1234567890", "2022");

        when(bookRepository.findByIsbn(any())).thenReturn(Optional.of(new Book()));

        assertThrows(LibraryManagementException.class, () -> bookService.addNewBook(bookDto));
    }

    @Test
    void updateABook_WithValidBookId_ShouldUpdateBook() throws LibraryManagementException {
        long bookId = 1;
        BookDto bookDto = new BookDto("Title", "Author", "2022", "1234567890");
        Book savedBook = new Book();

        when(bookRepository.findById(any())).thenReturn(Optional.of(savedBook));
        when(bookRepository.save(any())).thenReturn(savedBook);

        boolean result = bookService.updateABook(bookId, bookDto);

        assertTrue(result);
    }

    @Test
    void updateABook_WithInvalidPublicationYear_ShouldThrowLibraryManagementException() {
        long bookId = 1;
        BookDto bookDto = new BookDto("Title", "Author", "1234567890", "22"); // Invalid year

        assertThrows(LibraryManagementException.class, () -> bookService.updateABook(bookId, bookDto));
    }

    @Test
    void getBookById_WithValidBookId_ShouldReturnBook() throws LibraryManagementException {
        long bookId = 1;
        Book expectedBook = new Book();

        when(bookRepository.findById(any())).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getBookById(bookId);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    void getBookById_WithInvalidBookId_ShouldThrowLibraryManagementException() {
        long bookId = 1;

        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(LibraryManagementException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book());
        expectedBooks.add(new Book());

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAllBooks();

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void deleteBookById_WithValidBookId_ShouldDeleteBook() throws LibraryManagementException {
        long bookId = 1;

        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));

        boolean result = bookService.deleteBookById(bookId);

        assertTrue(result);
    }

    @Test
    void deleteBookById_WithInvalidBookId_ShouldThrowLibraryManagementException() {
        long bookId = 1;

        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(LibraryManagementException.class, () -> bookService.deleteBookById(bookId));
    }

}
