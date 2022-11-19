package com.loancalculator.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ApplicationException extends RuntimeException {

    private final String message;

    /**
     * Constructs a new runtime exception
     */
    public ApplicationException(String message) {
        this.message = message;
    }
}
