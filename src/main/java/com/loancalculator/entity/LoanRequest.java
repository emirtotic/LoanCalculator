package com.loancalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loan_request", schema = "loan-calculator")
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "loan_amount")
    private BigDecimal loanAmount;

    @NotNull
    @Column(name = "interest_rate")
    private Double interestRate;

    @NotNull
    @Column(name = "loan_term_years")
    private Integer loanTermYears;

    @OneToMany(mappedBy = "loanRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonthlyLoanCalculation> monthlyLoanResponses;
}
