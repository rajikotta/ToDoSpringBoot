package com.raji.todo.controller;

import com.raji.todo.models.dto.SignupRequestDto;
import com.raji.todo.models.dto.LoginRequestDto;
import com.raji.todo.models.dto.TokenPairDto;
import com.raji.todo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody SignupRequestDto signupRequest) {
        authService.creteAccount(signupRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public TokenPairDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }


    @PostMapping("/refresh")
    public TokenPairDto refresh(@RequestBody TokenPairDto tokenPairDto) {
        return authService.refresh(tokenPairDto);
    }
}
