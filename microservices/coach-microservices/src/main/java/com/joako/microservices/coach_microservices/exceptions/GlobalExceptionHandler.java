package com.joako.microservices.coach_microservices.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ApiExceptionResponse> handleApiErrorException(ApiErrorException ex, WebRequest webRequest) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .timeStamp(Instant.now())
                .error(ex.getError())
                .message(ex.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestApiException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadRequest(BadRequestApiException ex, WebRequest webRequest) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .timeStamp(Instant.now())
                .error(ex.getError())
                .message(ex.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundApiException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(NotFoundApiException ex,
            WebRequest webRequest) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .timeStamp(Instant.now())
                .error(ex.getError())
                .message(ex.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.NOT_FOUND);
    }

     @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest webRequest){

        Map<String,String> mapErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String clave = ((FieldError) error).getField();
            String valor = error.getDefaultMessage();

            mapErrors.put(clave, valor);
        });

        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
        .timeStamp(Instant.now())
        .error(mapErrors.toString())
        .message("Hubo un error al completar un campo")
        .details(webRequest.getDescription(false))
        .build();

        return new ResponseEntity<>(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
