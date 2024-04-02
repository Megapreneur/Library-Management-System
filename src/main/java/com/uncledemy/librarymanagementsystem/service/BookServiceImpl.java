package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.librarymanagementsystem.dto.BookDto;
import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    @Override
    public boolean addNewBook(BookDto bookDto) throws LibraryManagementException {
        Book savedBook = bookRepository.findByIsbn(bookDto.getIsbn()).orElseThrow(()->
                new LibraryManagementException("A Book with isbn: "+bookDto.getIsbn()+" already exist. "));
        Book newBook = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .publicationYear(bookDto.getPublicationYear())
                .availableForBorrowing(true)
                .build();
        bookRepository.save(newBook);
        return true;
    }

    @Override
    public boolean updateABook(long bookId,BookDto bookDto) throws LibraryManagementException {
        if (!isValidYear(bookDto.getPublicationYear())) throw new LibraryManagementException("The published year must be a 4 digit number");
        Book savedBook = getBookById(bookId);
        savedBook.setAuthor(bookDto.getAuthor());
        savedBook.setTitle(bookDto.getTitle());
        savedBook.setPublicationYear(bookDto.getPublicationYear());
        savedBook.setIsbn(bookDto.getIsbn());
        bookRepository.save(savedBook);
        return true;
    }

    @Override
    public Book getBookById(long bookId) throws LibraryManagementException {

        return bookRepository.findById(bookId).orElseThrow(()->
                new LibraryManagementException("Book with ID: "+bookId+" does not exist"));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public boolean deleteBookById(long bookId) throws LibraryManagementException {
        Book book = getBookById(bookId);
        bookRepository.delete(book);
        return true;
    }

    private boolean isValidYear(String password){
//        year must have 4 digits only
        return password.matches("^\\d{4}$");
    }
}
