package com.loancalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "monthly_loan_calculation", schema = "loan-calculator")
public class MonthlyLoanCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "month")
    private Integer month;

    @NotNull
    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @NotNull
    @Column(name = "principal_amount")
    private BigDecimal principalAmount;

    @NotNull
    @Column(name = "interest_amount")
    private BigDecimal interestAmount;

    @NotNull
    @Column(name = "balance_owed")
    private BigDecimal balanceOwed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private LoanRequest loanRequest;
}
