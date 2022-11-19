package com.loancalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CalculationNotFoundException extends ApplicationException {

    /**
     * Constructs a new CalculationNotFoundException exception
     *
     * @param calculationId
     */
    public CalculationNotFoundException(Long calculationId) {
        super(String.format("Loan Calculation with id=[%s] is not found", calculationId));
    }
}
