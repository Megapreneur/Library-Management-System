package com.uncledemy.librarymanagementsystem.controller;

import com.uncledemy.librarymanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.librarymanagementsystem.dto.LoginDto;
import com.uncledemy.librarymanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.librarymanagementsystem.exception.UserNotFoundException;
import com.uncledemy.librarymanagementsystem.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(
        name = "User Controller for login",
        description = "This class implements user's authentication in order to use the application."
)
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginDto loginDto) throws UserNotFoundException, InvalidPasswordException {
        return ResponseEntity.ok(authService.login(loginDto));
    }

}
