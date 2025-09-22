package com.felipe.user.infra.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ValidationException extends RuntimeException
{
    private final String action;
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ValidationException(String message, String action) {
        super(message);
        this.action = action;
    }
}

