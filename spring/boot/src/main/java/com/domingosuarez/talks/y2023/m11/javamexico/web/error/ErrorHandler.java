package com.domingosuarez.talks.y2023.m11.javamexico.web.error;

import com.domingosuarez.talks.y2023.m11.javamexico.services.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse resourceNotFoundException(BusinessException ex) {
        return ErrorResponse.builder()
            .message(ex.getMessage())
            .build();
    }
}
