package com.uncledemy.librarymanagementsystem.service;


import com.uncledemy.librarymanagementsystem.dto.UserDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.model.User;

public interface UserService {
    void registration(UserDto userDto) throws InvalidPasswordException, UserAlreadyExistException, PasswordMisMatchException, InvalidEmailException;
    User loadUser(String username) throws InvalidUsernameException;
    

}
