package com.bol.kalahagame.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private List<ValidationError> validationErrors = new ArrayList<>();

    public ErrorResponse(int value, String message) {
        this.status = value;
        this.message = message;
    }
}
