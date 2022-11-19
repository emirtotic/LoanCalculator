package com.loancalculator.service;

import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.dto.LoanResponseDTO;

public interface LoanCalculatorService {

    LoanResponseDTO findById(long id);

    LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO);

    void deleteLoan(long id);
}