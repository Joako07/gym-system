package com.joako.microservices.auth_microservices.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joako.microservices.auth_microservices.models.requests.LoginRequest;
import com.joako.microservices.auth_microservices.models.requests.RegisterRequest;
import com.joako.microservices.auth_microservices.models.responses.AuthResponse;
import com.joako.microservices.auth_microservices.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

      private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequesty) {
        return ResponseEntity.ok(authService.login(loginRequesty));
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) { 
        return ResponseEntity.ok(authService.register(registerRequest));
    }
    
}
