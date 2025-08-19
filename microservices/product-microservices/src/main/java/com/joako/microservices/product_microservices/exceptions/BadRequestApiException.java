package com.joako.microservices.product_microservices.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@Builder
public class BadRequestApiException extends RuntimeException{

    private final String error;
    private final String message;

    public String getError(){
        return error;
    }

}
