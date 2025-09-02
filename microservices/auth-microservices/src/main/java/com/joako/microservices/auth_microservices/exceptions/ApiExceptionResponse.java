package com.joako.microservices.auth_microservices.exceptions;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiExceptionResponse {

    private Instant timeStamp;
    private String error;
    private String message;
    private String details;

}
