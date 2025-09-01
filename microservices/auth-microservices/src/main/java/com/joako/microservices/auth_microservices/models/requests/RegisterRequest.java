package com.joako.microservices.auth_microservices.models.requests;

import com.joako.microservices.auth_microservices.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private Role role;
    
}
