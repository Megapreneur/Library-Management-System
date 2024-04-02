package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;

public interface BorrowingService {
    void borrowBook(long bookId, long patronId) throws LibraryManagementException;
    void returnBook(long bookId, long patronId) throws LibraryManagementException;
}
