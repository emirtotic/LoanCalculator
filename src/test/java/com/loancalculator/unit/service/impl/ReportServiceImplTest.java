package com.loancalculator.unit.service.impl;

import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.dto.MonthlyLoanCalculationDTO;
import com.loancalculator.service.JasperService;
import com.loancalculator.service.LoanCalculatorService;
import com.loancalculator.service.ReportService;
import com.loancalculator.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReportServiceImplTest {

    private LoanCalculatorService loanCalculatorService;
    private JasperService jasperService;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        loanCalculatorService = mock(LoanCalculatorService.class);
        jasperService = mock(JasperService.class);
        reportService = new ReportServiceImpl(loanCalculatorService, jasperService);
    }

    @Test
    @DisplayName("Generating PDF Loan Report Test")
    void createGeneralPdfReportTest() {

        LoanResponseDTO loanResponseDTO = createLoanResponseDTO();

        when(jasperService.exportReportToPDF(anyString(), anyMap(), anyCollection()))
                .thenReturn(new ByteArrayInputStream(new byte[0]));
        when(loanCalculatorService.findById(anyLong())).thenReturn(loanResponseDTO);

        reportService.createGeneralPdfReport(3L);
        verify(jasperService, times(1)).exportReportToPDF(anyString(), anyMap(), anyCollection());
    }

    @Test
    @DisplayName("Generating Excel Loan Report Test")
    void createGeneralExcelReportTest() {

        LoanResponseDTO loanResponseDTO = createLoanResponseDTO();

        when(jasperService.exportReportToPDF(anyString(), anyMap(), anyCollection()))
                .thenReturn(new ByteArrayInputStream(new byte[0]));
        when(loanCalculatorService.findById(anyLong())).thenReturn(loanResponseDTO);

        reportService.createGeneralExcelReport(3L);
        verify(jasperService, times(1)).exportReportExcel(anyString(), anyMap(), anyCollection());
    }

    private LoanResponseDTO createLoanResponseDTO() {

        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setLoanAmount(new BigDecimal(800.00));
        loanResponseDTO.setInterestRate(6.1);
        loanResponseDTO.setLoanTerm(1);
        loanResponseDTO.setTotalPayments(new BigDecimal(2222.00));
        loanResponseDTO.setTotalInterest(new BigDecimal(400.00));
        loanResponseDTO.setMonthlyLoanResponsesDTO(
                Collections.singletonList(
                        createMonthlyLoanCalculationDTO(1, new BigDecimal(68.89),
                                new BigDecimal(64.82), new BigDecimal(4.07), new BigDecimal(735.18))));

        return loanResponseDTO;
    }

    private MonthlyLoanCalculationDTO createMonthlyLoanCalculationDTO(int month, BigDecimal paymentAmount,
                                                                      BigDecimal principalAmount,
                                                                      BigDecimal interestAmount,
                                                                      BigDecimal balanceOwed) {

        MonthlyLoanCalculationDTO calculationDTO = new MonthlyLoanCalculationDTO();
        calculationDTO.setMonth(month);
        calculationDTO.setPaymentAmount(paymentAmount);
        calculationDTO.setPrincipalAmount(principalAmount);
        calculationDTO.setInterestAmount(interestAmount);
        calculationDTO.setBalanceOwed(balanceOwed);

        return calculationDTO;
    }
}