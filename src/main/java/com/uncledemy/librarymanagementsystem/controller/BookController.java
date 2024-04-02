package com.uncledemy.librarymanagementsystem.controller;

import com.uncledemy.librarymanagementsystem.constants.StatusConstant;
import com.uncledemy.librarymanagementsystem.dto.BookDto;
import com.uncledemy.librarymanagementsystem.dto.ResponseDto;
import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;
    @PostMapping("")
    public ResponseEntity<ResponseDto> addABook(@Valid @RequestBody BookDto bookDto) throws LibraryManagementException {
        boolean isCreated = bookService.addNewBook(bookDto);
        if (isCreated){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(StatusConstant.STATUS_201, StatusConstant.MESSAGE_201));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_CREATE));
        }

    }
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findABook(@PathVariable("bookId") long bookId) throws LibraryManagementException {
        Book book = bookService.getBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
    @PutMapping("/{bookId}")
    public ResponseEntity<ResponseDto> updateABook(@Valid @PathVariable("bookId") long bookId, @Valid @RequestBody BookDto bookDto) throws LibraryManagementException {
        boolean isUpdated = bookService.updateABook(bookId, bookDto);
        if (isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(StatusConstant.STATUS_200, StatusConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_UPDATE));
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable("bookId") long bookId) throws LibraryManagementException {
        boolean isDeleted = bookService.deleteBookById(bookId);
        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(StatusConstant.STATUS_200, StatusConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_DELETE));
        }
    }
}
