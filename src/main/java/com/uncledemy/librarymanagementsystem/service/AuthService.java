package com.uncledemy.librarymanagementsystem.service;


import com.uncledemy.librarymanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.librarymanagementsystem.dto.LoginDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.UserNotFoundException;

public interface AuthService {
    AuthenticationResponse login(LoginDto loginDto) throws SalesManagementException, UserNotFoundException, InvalidPasswordException;
}
