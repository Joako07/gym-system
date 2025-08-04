package com.joako.microservices.client_microservices.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NotFoundApiException extends RuntimeException{

    private final String error;
    private final String message;

    public String getError() {
        return error;
    }
}
