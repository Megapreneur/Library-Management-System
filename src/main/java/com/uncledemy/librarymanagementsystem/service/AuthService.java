package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.salesmanagementsystem.dto.LoginDto;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.exception.UserNotFoundException;

public interface AuthService {
    AuthenticationResponse login(LoginDto loginDto) throws SalesManagementException, UserNotFoundException;
}
