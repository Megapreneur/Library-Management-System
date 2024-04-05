package com.uncledemy.librarymanagementsystem.controller;

import com.uncledemy.librarymanagementsystem.constants.StatusConstant;
import com.uncledemy.librarymanagementsystem.dto.BookDto;
import com.uncledemy.librarymanagementsystem.dto.ResponseDto;
import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@CacheConfig(cacheNames = "books")
@Slf4j
@Tag(
        name = "Book Controller for All CRUD API",
        description = "This class implements all the CRUD api operations for the Book Management."
)
public class BookController {

    private final BookService bookService;
    @PostMapping("/")
    public ResponseEntity<ResponseDto> addABook(@Valid @RequestBody BookDto bookDto) throws LibraryManagementException {
        boolean isCreated = bookService.addNewBook(bookDto);
        if (isCreated){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(StatusConstant.STATUS_201, StatusConstant.MESSAGE_201_BOOK));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_CREATE));
        }

    }
    @GetMapping("/{bookId}")
    @Cacheable(key = "#bookId")
    public ResponseEntity<Book> findABook(@PathVariable("bookId") long bookId) throws LibraryManagementException {
        Book book = bookService.getBookById(bookId);
        log.info("Getting book with id {} from DB.",bookId);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
    @PutMapping("/{bookId}")
    @CachePut(key = "#bookId")
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
    @Cacheable(key = "'allBooks'")
    public ResponseEntity<List<Book>> getAllBooks(){
        log.info("Getting all Books from DB");

        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @DeleteMapping("/{bookId}")
    @CacheEvict(key = "#bookId")
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
