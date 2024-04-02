package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.model.BorrowingRecord;
import com.uncledemy.librarymanagementsystem.model.Patron;
import com.uncledemy.librarymanagementsystem.repository.BookRepository;
import com.uncledemy.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.uncledemy.librarymanagementsystem.repository.PatronRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowingService{
    private final BookRepository bookRepository;

    private final PatronRepository patronRepository;

    private final BorrowingRecordRepository borrowingRecordRepository;

    @Override
    @Transactional
    public void borrowBook(long bookId, long patronId) throws LibraryManagementException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryManagementException("Book not found with ID: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new LibraryManagementException("Patron not found with ID: " + patronId));

        if (!book.isAvailableForBorrowing()) {
            throw new LibraryManagementException("Book is not available for borrowing.");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(new Date());
        borrowingRecordRepository.save(borrowingRecord);

        book.setAvailableForBorrowing(false);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void returnBook(long bookId, long patronId) throws LibraryManagementException {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);

        if (borrowingRecord == null) {
            throw new LibraryManagementException("Borrowing record not found for book ID: " + bookId + " and patron ID: " + patronId);
        }

        borrowingRecord.setReturnDate(new Date());
        borrowingRecordRepository.save(borrowingRecord);

        Book book = borrowingRecord.getBook();
        book.setAvailableForBorrowing(true);
        bookRepository.save(book);
    }
}
