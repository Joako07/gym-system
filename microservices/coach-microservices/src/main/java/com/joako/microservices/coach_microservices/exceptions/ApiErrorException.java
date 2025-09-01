package com.joako.microservices.coach_microservices.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@Builder
public class ApiErrorException extends RuntimeException {

    private final String error; 
    private final String message;

    public String getError(){
        return error;
    }

}
