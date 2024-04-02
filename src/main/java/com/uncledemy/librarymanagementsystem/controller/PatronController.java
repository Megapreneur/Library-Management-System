package com.uncledemy.librarymanagementsystem.controller;

import com.uncledemy.librarymanagementsystem.constants.StatusConstant;
import com.uncledemy.librarymanagementsystem.dto.BookDto;
import com.uncledemy.librarymanagementsystem.dto.PatronDto;
import com.uncledemy.librarymanagementsystem.dto.ResponseDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.librarymanagementsystem.exception.LibraryManagementException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.model.Patron;
import com.uncledemy.librarymanagementsystem.service.BookService;
import com.uncledemy.librarymanagementsystem.service.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PatronController {
    private final PatronService patronService;
    @PostMapping("")
    public ResponseEntity<ResponseDto> addABook(@Valid @RequestBody PatronDto patronDto) throws UserAlreadyExistException, InvalidPhoneNumberException, InvalidEmailException {
        boolean isCreated = patronService.addNewPatron(patronDto);
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
    @GetMapping("/{patronId}")
    public ResponseEntity<Patron> findAPatron(@PathVariable("patronId") long patronId){
        Patron patron = patronService.getPatronById(patronId);
        return ResponseEntity.status(HttpStatus.OK).body(patron);
    }
    @PutMapping("/{patronId}")
    public ResponseEntity<ResponseDto> updateAPatron(@Valid @PathVariable("patronId") long patronId, @Valid @RequestBody PatronDto patronDto) throws InvalidPhoneNumberException, InvalidEmailException {
        boolean isUpdated = patronService.updateAPatron(patronId, patronDto);
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
    public ResponseEntity<List<Patron>> getAllPatrons(){
        List<Patron> patrons = patronService.getAllPatrons();
        return ResponseEntity.status(HttpStatus.OK).body(patrons);
    }

    @DeleteMapping("/{patronId}")
    public ResponseEntity<ResponseDto> deletePatron(@PathVariable("patronId") long patronId) {
        boolean isDeleted = patronService.deletePatronById(patronId);
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
