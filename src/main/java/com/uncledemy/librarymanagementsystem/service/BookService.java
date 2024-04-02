package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.librarymanagementsystem.dto.BookDto;
import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.model.Book;

import java.util.List;

public interface BookService {
    boolean addNewBook(BookDto bookDto) throws LibraryManagementException;
    boolean updateABook(long bookId,BookDto bookDto) throws LibraryManagementException;
    Book getBookById(long bookId) throws LibraryManagementException;
    List<Book> getAllBooks();
    boolean deleteBookById(long bookId) throws LibraryManagementException;
}
