package com.loancalculator.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoanResponseDTO {

    private Long id;

    @NotNull
    private BigDecimal loanAmount;

    @Positive
    private double interestRate;

    @Positive
    @Min(12)
    @Max(420)
    private int loanTerm;

    @NotNull
    @Positive
    private BigDecimal totalPayments;

    @NotNull
    @Positive
    private BigDecimal totalInterest;

    private List<MonthlyLoanCalculationDTO> monthlyLoanResponsesDTO = new ArrayList<>();
}
