package com.loancalculator.unit.controller;

import com.loancalculator.controller.LoanController;
import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.service.LoanCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LoanControllerTest {

    private LoanCalculatorService loanCalculatorService;
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        loanCalculatorService = mock(LoanCalculatorService.class);
        loanController = new LoanController(loanCalculatorService);
    }

    @Test
    @DisplayName("Create Loan Test")
    void createTest() {

        LoanRequestDTO loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setLoanAmount(new BigDecimal(12200.00));

        LoanResponseDTO responseDTO = new LoanResponseDTO();
        responseDTO.setLoanAmount(new BigDecimal(12200.00));

        when(loanCalculatorService.createLoan(any())).thenReturn(responseDTO);
        LoanResponseDTO response = loanController.create(loanRequestDTO).getBody();
        assertEquals(loanRequestDTO.getLoanAmount(), response.getLoanAmount());
        verify(loanCalculatorService, times(1)).createLoan(any());
    }

    @Test
    @DisplayName("Find Loan Request by ID Test")
    void findByIdTest() {

        when(loanCalculatorService.findById(anyLong())).thenReturn(new LoanResponseDTO());
        LoanResponseDTO responseDTO = loanController.findById(100000L).getBody();
        assertNotNull(responseDTO);
        verify(loanCalculatorService, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Delete Loan Request Test")
    void deleteByIdTest() {
        loanController.deleteById(10000L);
        verify(loanCalculatorService, times(1)).deleteLoan(anyLong());
    }
}