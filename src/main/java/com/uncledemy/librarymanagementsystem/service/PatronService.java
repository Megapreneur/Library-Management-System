package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.librarymanagementsystem.dto.PatronDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.model.Book;
import com.uncledemy.librarymanagementsystem.model.Patron;

import java.util.List;

public interface PatronService {
    boolean addNewPatron(PatronDto bookDto) throws UserAlreadyExistException, InvalidEmailException, InvalidPhoneNumberException;
    boolean updateAPatron(long patronId, PatronDto bookDto) throws InvalidEmailException, InvalidPhoneNumberException;
    Patron getPatronById(long patronId);
    List<Patron> getAllPatrons();
    boolean deletePatronById(long patronId);
}
