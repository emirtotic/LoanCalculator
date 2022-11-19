package com.loancalculator.entity;

import com.loancalculator.dto.MonthlyLoanCalculationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanReport {

    private BigDecimal loanAmount;
    private Double interestRate;
    private Integer loanTermYears;
    private Integer loanTermMonths;
    private BigDecimal totalPayments;
    private BigDecimal totalInterest;
    private List<MonthlyLoanCalculationDTO> monthlyLoanResponsesDTO;
}
