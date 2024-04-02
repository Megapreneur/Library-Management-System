package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.StaffDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.salesmanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.salesmanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.salesmanagementsystem.model.Authority;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.repository.UserRepository;
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
    public void staffRegistration(StaffDto staffDto) throws InvalidPasswordException, UserAlreadyExistException, PasswordMisMatchException {
        if (!isValidPassword(staffDto.getPassword())){
            throw new InvalidPasswordException("password must have at least 8 characters, 1 upper case, 1 number, 1 special character");
        }
        Optional<User> savedUser = userRepository.findUserByUsername(staffDto.getUsername().toLowerCase());
        if (savedUser.isPresent()) {
            throw new UserAlreadyExistException("A staff with this username already exist !!!");
        } else {
            if (staffDto.getPassword().equals(staffDto.getConfirmPassword())){
                User newUser = new User();
                newUser.setName(staffDto.getName());
                newUser.setUsername(staffDto.getUsername().toLowerCase());
                newUser.setAuthority(Authority.valueOf(staffDto.getAuthority()));
                newUser.setPassword(passwordEncoder.encode(staffDto.getPassword()));
                userRepository.save(newUser);
                logger.info("User registered: {}", newUser.getUsername());
            }else{
                throw new PasswordMisMatchException("Password does not match");
            }

        }
    }

    @Override
    public User loadUser(String username){
        return userRepository.findUserByUsernameIgnoreCase(username);
    }

    private boolean isValidPassword(String password){
//        password must have at least 8 characters, 1 upper case, 1 number, 1 special character
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$");
    }
}
