package com.uncledemy.librarymanagementsystem.controller;

import com.uncledemy.librarymanagementsystem.constants.StatusConstant;
import com.uncledemy.librarymanagementsystem.dto.ResponseDto;
import com.uncledemy.librarymanagementsystem.dto.UserDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")

    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody UserDto userDto) throws UserAlreadyExistException, InvalidPasswordException, InvalidEmailException, PasswordMisMatchException {
        userService.registration(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(StatusConstant.STATUS_201, StatusConstant.MESSAGE_201));
    }
}
