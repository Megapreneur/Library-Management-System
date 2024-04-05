package com.uncledemy.librarymanagementsystem.controller;

import com.uncledemy.librarymanagementsystem.service.BorrowingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(
        name = "Borrowing Controller",
        description = "This class implements the borrowing and returning of books by the patron."
)
public class BorrowingController {


    private final BorrowingService borrowingService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            borrowingService.borrowBook(bookId, patronId);
            return ResponseEntity.ok("Book borrowed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error borrowing book: " + e.getMessage());
        }
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            borrowingService.returnBook(bookId, patronId);
            return ResponseEntity.ok("Book returned successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error returning book: " + e.getMessage());
        }
    }
}
