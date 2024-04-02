package com.uncledemy.librarymanagementsystem.service;


import com.uncledemy.librarymanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.librarymanagementsystem.dto.LoginDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.UserNotFoundException;
import com.uncledemy.librarymanagementsystem.model.User;
import com.uncledemy.librarymanagementsystem.repository.UserRepository;
import com.uncledemy.librarymanagementsystem.security.JwtService;
import com.uncledemy.librarymanagementsystem.security.config.SecureUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse login(LoginDto loginDto) throws UserNotFoundException, InvalidPasswordException {
        Optional<User> savedUser = userRepository.findUserByEmail(loginDto.getEmail().toLowerCase());
        if (savedUser.isPresent()){

                try {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginDto.getEmail().toLowerCase(), loginDto.getPassword())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (BadCredentialsException e) {
                    logger.info("Authentication failed for : {}", loginDto.getEmail());

                    throw new InvalidPasswordException(e.getLocalizedMessage());
                }
                User foundUser = userRepository.findUserByEmail(loginDto.getEmail().toLowerCase()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
                SecureUser user = new SecureUser(foundUser);
                String jwtToken = jwtService.generateToken(user);
            logger.info("Authentication was successful for : {}", loginDto.getEmail());

                return AuthenticationResponse.of(jwtToken, user.getUserId());
        }
        throw new UserNotFoundException("User not found!!!");
    }
}
