package com.loancalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanRequestDTO {

    @Positive
    @NotNull
    private BigDecimal loanAmount;

    @Positive
    private double interestRate;

    @Positive
    @Min(1)
    @Max(35)
    private int loanTermYears;
}