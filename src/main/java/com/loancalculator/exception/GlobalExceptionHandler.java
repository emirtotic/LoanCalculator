package com.loancalculator.exception;

import com.loancalculator.error.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This is handling specific exception method
     *
     * @param exception
     * @param webRequest
     * @return ResponseEntity<ErrorDetails>
     */
    @ExceptionHandler(CalculationNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            CalculationNotFoundException exception,
            WebRequest webRequest) {

        log.error("CalculationNotFoundException Occurred!");

        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
