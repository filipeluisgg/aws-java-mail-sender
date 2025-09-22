package com.felipe.user.infra.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class NotFoundException extends RuntimeException
{
    private final String action;
    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String message, String action) {
        super(message);
        this.action = action;
    }
}

