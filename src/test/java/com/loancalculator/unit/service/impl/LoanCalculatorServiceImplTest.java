package com.loancalculator.unit.service.impl;

import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.entity.LoanRequest;
import com.loancalculator.entity.MonthlyLoanCalculation;
import com.loancalculator.exception.CalculationNotFoundException;
import com.loancalculator.mapper.LoanMapper;
import com.loancalculator.mapper.MonthlyCalculationMapper;
import com.loancalculator.repository.LoanCalculatorRepository;
import com.loancalculator.service.LoanCalculatorService;
import com.loancalculator.service.impl.LoanCalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LoanCalculatorServiceImplTest {

    private LoanCalculatorRepository loanCalculatorRepository;
    private LoanMapper loanMapper;
    private MonthlyCalculationMapper monthlyCalculationMapper;
    private LoanCalculatorService loanCalculatorService;

    @BeforeEach
    void setUp() {

        loanCalculatorRepository = mock(LoanCalculatorRepository.class);
        loanMapper = mock(LoanMapper.class);
        monthlyCalculationMapper = Mappers.getMapper(MonthlyCalculationMapper.class);
        loanCalculatorService = new LoanCalculatorServiceImpl(
                loanCalculatorRepository, loanMapper, monthlyCalculationMapper);
    }

    @Test
    @DisplayName("Find Loan Calculation by ID Test")
    void findByIdTest() {

        LoanRequest loanRequest = createLoanRequest();

        when(loanCalculatorRepository.findLoanRequest(any())).thenReturn(Optional.of(loanRequest));
        when(loanMapper.mapToResponse(any())).thenReturn(new LoanResponseDTO());

        loanCalculatorService.findById(anyLong());

        verify(loanCalculatorRepository, times(1)).findLoanRequest(anyLong());
        verify(loanMapper, times(1)).mapToResponse(any());
    }

    @Test
    @DisplayName("Find Loan Calculation by ID Throws Exception Test")
    void findByIdThrowsExceptionTest() {

        when(loanCalculatorRepository.findLoanRequest(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                CalculationNotFoundException.class,
                () -> loanCalculatorService.findById(8L));

        verify(loanCalculatorRepository, times(1)).findLoanRequest(anyLong());
    }

    @Test
    @DisplayName("Create Loan Calculation Test")
    void createLoanTest() {

        LoanRequestDTO loanRequestDTO = createLoanRequestDTO();
        LoanRequest loanRequest = createLoanRequest();

        when(loanMapper.mapToEntity(any())).thenReturn(loanRequest);
        when(loanMapper.mapToResponse(any())).thenReturn(new LoanResponseDTO());
        when(loanCalculatorRepository.save(any())).thenReturn(loanRequest);

        loanCalculatorService.createLoan(loanRequestDTO);

        verify(loanMapper, times(1)).mapToEntity(any());
        verify(loanMapper, times(1)).mapToResponse(any());
        verify(loanCalculatorRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Delete Loan Calculation by ID Test")
    void deleteLoanTest() {

        when(loanCalculatorRepository.findById(anyLong())).thenReturn(Optional.of(new LoanRequest()));
        doNothing().when(loanCalculatorRepository).deleteById(anyLong());
        loanCalculatorService.deleteLoan(1L);
        verify(loanCalculatorRepository, times(1)).deleteById(anyLong());
    }

    private LoanRequest createLoanRequest() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setId(1L);
        loanRequest.setLoanAmount(new BigDecimal(800.00));
        loanRequest.setInterestRate(6.1);
        loanRequest.setLoanTermYears(1);
        loanRequest.setMonthlyLoanResponses(createMonthlyLoanCalculationList());

        return loanRequest;
    }

    private List<MonthlyLoanCalculation> createMonthlyLoanCalculationList() {

        List<MonthlyLoanCalculation> calculations = new ArrayList<>();
        calculations.add(createMonthlyLoanCalculation(
                1, new BigDecimal(68.89), new BigDecimal(64.82), new BigDecimal(4.07), new BigDecimal(735.18)));
        calculations.add(createMonthlyLoanCalculation(
                2, new BigDecimal(68.89), new BigDecimal(65.15), new BigDecimal(3.74), new BigDecimal(670.03)));
        calculations.add(createMonthlyLoanCalculation(
                3, new BigDecimal(68.89), new BigDecimal(65.48), new BigDecimal(3.41), new BigDecimal(604.55)));
        calculations.add(createMonthlyLoanCalculation(
                4, new BigDecimal(68.89), new BigDecimal(65.82), new BigDecimal(3.07), new BigDecimal(538.73)));
        calculations.add(createMonthlyLoanCalculation(
                5, new BigDecimal(68.89), new BigDecimal(66.15), new BigDecimal(2.74), new BigDecimal(472.58)));
        calculations.add(createMonthlyLoanCalculation(
                6, new BigDecimal(68.89), new BigDecimal(66.49), new BigDecimal(2.40), new BigDecimal(406.09)));
        calculations.add(createMonthlyLoanCalculation(
                7, new BigDecimal(68.89), new BigDecimal(66.83), new BigDecimal(2.06), new BigDecimal(339.26)));
        calculations.add(createMonthlyLoanCalculation(
                8, new BigDecimal(68.89), new BigDecimal(67.17), new BigDecimal(1.72), new BigDecimal(272.09)));
        calculations.add(createMonthlyLoanCalculation(
                9, new BigDecimal(68.89), new BigDecimal(67.51), new BigDecimal(1.38), new BigDecimal(204.58)));
        calculations.add(createMonthlyLoanCalculation(
                10, new BigDecimal(68.89), new BigDecimal(67.85), new BigDecimal(1.04), new BigDecimal(136.73)));
        calculations.add(createMonthlyLoanCalculation(
                11, new BigDecimal(68.89), new BigDecimal(68.19), new BigDecimal(0.70), new BigDecimal(68.54)));
        calculations.add(createMonthlyLoanCalculation(
                12, new BigDecimal(68.89), new BigDecimal(68.54), new BigDecimal(0.35), new BigDecimal(0.00)));

        return calculations;
    }

    private MonthlyLoanCalculation createMonthlyLoanCalculation(int month, BigDecimal paymentAmount,
                                                                      BigDecimal principalAmount,
                                                                      BigDecimal interestAmount,
                                                                      BigDecimal balanceOwed) {

        MonthlyLoanCalculation calculation = new MonthlyLoanCalculation();
        calculation.setMonth(month);
        calculation.setPaymentAmount(paymentAmount);
        calculation.setPrincipalAmount(principalAmount);
        calculation.setInterestAmount(interestAmount);
        calculation.setBalanceOwed(balanceOwed);

        return calculation;
    }

    private LoanRequestDTO createLoanRequestDTO() {
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setLoanAmount(new BigDecimal(800.00));
        loanRequestDTO.setInterestRate(6.1);
        loanRequestDTO.setLoanTermYears(1);

        return loanRequestDTO;
    }
}