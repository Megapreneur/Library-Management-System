package com.uncledemy.librarymanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.salesmanagementsystem.dto.LoginDto;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.exception.UserNotFoundException;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.repository.UserRepository;
import com.uncledemy.salesmanagementsystem.security.JwtService;
import com.uncledemy.salesmanagementsystem.security.config.SecureUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public AuthenticationResponse login(LoginDto loginDto) throws SalesManagementException, UserNotFoundException {
        Optional<User> savedUser = userRepository.findUserByUsername(loginDto.getUsername().toLowerCase());
        if (savedUser.isPresent()){

                try {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginDto.getUsername().toLowerCase(), loginDto.getPassword())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (BadCredentialsException e) {
                    logger.info("Authentication failed for : {}", loginDto.getUsername());

                    throw new SalesManagementException(e.getLocalizedMessage());
                }
                User foundUser = userRepository.findUserByUsername(loginDto.getUsername().toLowerCase()).orElseThrow(() -> new SalesManagementException("user not found"));
                SecureUser user = new SecureUser(foundUser);
                String jwtToken = jwtService.generateToken(user);
            logger.info("Authentication was successful for : {}", loginDto.getUsername());

                return AuthenticationResponse.of(jwtToken, user.getUserId());
        }
        throw new UserNotFoundException("User not found!!!");
    }
}
