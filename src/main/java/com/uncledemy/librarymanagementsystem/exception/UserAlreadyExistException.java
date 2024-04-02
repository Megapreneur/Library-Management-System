package com.uncledemy.librarymanagementsystem.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
