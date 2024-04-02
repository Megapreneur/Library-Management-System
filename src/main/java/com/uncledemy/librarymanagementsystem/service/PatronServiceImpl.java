package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.librarymanagementsystem.dto.PatronDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.model.Patron;
import com.uncledemy.librarymanagementsystem.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService{
    public final PatronRepository patronRepository;
    @Override
    public boolean addNewPatron(PatronDto patronDto) throws UserAlreadyExistException, InvalidEmailException, InvalidPhoneNumberException {
        if (!isValidEmail(patronDto.getEmail())) throw new InvalidEmailException("The Email address provided is not valid");
        if (!isValidPhoneNumber(patronDto.getPhoneNumber())) throw new InvalidPhoneNumberException("The Phone number provided is not valid");
        Patron savedPatron = patronRepository.findByEmail(patronDto.getEmail()).orElseThrow(()->
                new UserAlreadyExistException("A Patron with email: "+patronDto.getEmail()+" already exist"));
        Patron newPatron = Patron.builder()
                .firstName(patronDto.getFirstName())
                .lastName(patronDto.getLastName())
                .email(patronDto.getEmail())
                .phoneNumber(patronDto.getPhoneNumber())
                .homeAddress(patronDto.getAddress())
                .build();
        patronRepository.save(newPatron);

        return true;
    }

    @Override
    public boolean updateAPatron(long patronId,PatronDto patronDto) throws InvalidEmailException, InvalidPhoneNumberException {
        if (!isValidEmail(patronDto.getEmail())) throw new InvalidEmailException("The Email address provided is not valid");
        if (!isValidPhoneNumber(patronDto.getPhoneNumber())) throw new InvalidPhoneNumberException("The Phone number provided is not valid");
        Patron savedPatron = getPatronById(patronId);
        savedPatron.setFirstName(patronDto.getFirstName());
        savedPatron.setLastName(patronDto.getLastName());
        savedPatron.setPhoneNumber(patronDto.getPhoneNumber());
        savedPatron.setEmail(patronDto.getEmail());
        savedPatron.setHomeAddress(patronDto.getAddress());
        patronRepository.save(savedPatron);

        return true;
    }

    @Override
    public Patron getPatronById(long patronId) {
        return patronRepository.findById(patronId).orElseThrow(()->
                new UsernameNotFoundException("Patron with ID: "+patronId+" does not exist"));
    }

    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public boolean deletePatronById(long patronId) {
        Patron patron = getPatronById(patronId);
        patronRepository.delete(patron);
        return true;
    }

    private boolean isValidEmail(String email){
//        email must only contain letters, numbers, underscores, hyphens, and periods
//        email must contain an @ symbol
//        email must contain . after @ symbol
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches( "^[0-9+]+$");
    }

}
