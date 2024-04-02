package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.librarymanagementsystem.dto.UserDto;

import com.uncledemy.librarymanagementsystem.exception.InvalidEmailException;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.librarymanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.librarymanagementsystem.model.Authority;
import com.uncledemy.librarymanagementsystem.model.User;
import com.uncledemy.librarymanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registration(UserDto userDto) throws InvalidPasswordException, UserAlreadyExistException, PasswordMisMatchException, InvalidEmailException {
        if (!isValidPassword(userDto.getPassword()))
            throw new InvalidPasswordException("password must have at least 8 characters, 1 upper case, 1 number, 1 special character");
        if (!isValidEmail(userDto.getEmail()))
            throw new InvalidEmailException(userDto.getEmail()+" is not a valid email address");

        Optional<User> savedUser = userRepository.findUserByEmail(userDto.getEmail().toLowerCase());
        if (savedUser.isPresent()) {
            throw new UserAlreadyExistException("A staff with this username already exist !!!");
        } else {
            if (userDto.getPassword().equals(userDto.getConfirmPassword())){
                User newUser = new User();
                newUser.setFirstName(userDto.getFirstName());
                newUser.setLastName(userDto.getLastName());
                newUser.setEmail(userDto.getEmail().toLowerCase());
                newUser.setAuthority(Authority.valueOf("ADMIN"));
                newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRepository.save(newUser);
                logger.info("User registered: {}", newUser.getFirstName());
            }else{
                throw new PasswordMisMatchException("Password does not match");
            }

        }
    }

    @Override
    public User loadUser(String username){
        return userRepository.findByEmail(username);
    }

    private boolean isValidPassword(String password){
//        password must have at least 8 characters, 1 upper case, 1 number, 1 special character
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$");
    }
    private boolean isValidEmail(String email){
//        email must only contain letters, numbers, underscores, hyphens, and periods
//        email must contain an @ symbol
//        email must contain . after @ symbol
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }
}
