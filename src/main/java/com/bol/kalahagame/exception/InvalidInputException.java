package com.bol.kalahagame.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@RequiredArgsConstructor
public class InvalidInputException extends RuntimeException {
    String field;
    String message;

    public InvalidInputException(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
