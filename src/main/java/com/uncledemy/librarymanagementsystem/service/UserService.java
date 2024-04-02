package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.StaffDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.salesmanagementsystem.exception.InvalidUsernameException;
import com.uncledemy.salesmanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.salesmanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.salesmanagementsystem.model.User;

public interface UserService {
    void staffRegistration(StaffDto staffDto) throws InvalidPasswordException, UserAlreadyExistException, PasswordMisMatchException;
    User loadUser(String username) throws InvalidUsernameException;
    

}
