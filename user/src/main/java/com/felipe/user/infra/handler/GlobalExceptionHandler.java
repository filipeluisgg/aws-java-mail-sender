package com.felipe.user.infra.handler;

import com.felipe.user.infra.exceptions.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RestErrorMessage> handleValidationError(ValidationException ex)
    {
        RestErrorMessage errorMessage = RestErrorMessage.builder()
                .name(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .action(ex.getAction())
                .status_code(ex.getHttpStatus().value())
                .build();

        return new ResponseEntity<>(errorMessage, ex.getHttpStatus());
    }
}

