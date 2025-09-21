package com.felipe.user.infra.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ValidationError extends RuntimeException
{
    private final String action;
    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    @Builder
    public ValidationError(String message, String action) {
        super(message);
        this.action = action;
    }
}

