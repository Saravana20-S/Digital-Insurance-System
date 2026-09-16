package com.insurance.auth.controller;

import com.insurance.auth.dto.auth.AuthResponse;
import com.insurance.auth.dto.auth.LoginRequest;
import com.insurance.auth.dto.auth.RegisterRequest;
import com.insurance.auth.service.AuthService;

import com.insurance.auth.dto.auth.MeResponse;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(Authentication authentication) {

        return ResponseEntity.ok(
                authService.getCurrentUser(authentication)
        );
    }
}