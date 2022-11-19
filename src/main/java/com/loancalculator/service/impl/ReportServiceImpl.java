package com.loancalculator.service.impl;

import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.entity.LoanReport;
import com.loancalculator.service.JasperService;
import com.loancalculator.service.LoanCalculatorService;
import com.loancalculator.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final LoanCalculatorService loanCalculatorService;
    private final JasperService jasperService;

    /**
     * Create PDF loan report
     *
     * @param id
     * @return InputStream for creating pdf report
     */
    @Override
    @Transactional(readOnly = true)
    public InputStream createGeneralPdfReport(Long id) {

        log.info("Generating PDF report for Loan Calculation.");

        Map<String, Object> params = new HashMap<>();
        LoanReport loanReport = prepareDataForLoanReport(id);
        params.put("loanReport", loanReport);

        return jasperService.exportReportToPDF("/report/loan-report.jrxml", params, loanReport.getMonthlyLoanResponsesDTO());
    }

    /**
     * Create Excel loan report
     *
     * @param id
     * @return InputStream for creating excel .xlsx report
     */
    @Override
    @Transactional(readOnly = true)
    public InputStream createGeneralExcelReport(Long id) {

        log.info("Generating Excel report for Loan Calculation.");

        Map<String, Object> params = new HashMap<>();
        LoanReport loanReport = prepareDataForLoanReport(id);
        params.put("loanReport", loanReport);
        return jasperService.exportReportExcel("/report/loan-report.jrxml", params, loanReport.getMonthlyLoanResponsesDTO());
    }

    private LoanReport prepareDataForLoanReport(Long loanId) {

        LoanReport loanReport = new LoanReport();
        LoanResponseDTO loanResponseDTO = loanCalculatorService.findById(loanId);

        loanReport.setLoanAmount(loanResponseDTO.getLoanAmount());
        loanReport.setInterestRate(loanResponseDTO.getInterestRate());
        loanReport.setLoanTermYears(loanResponseDTO.getLoanTerm() / 12);
        loanReport.setLoanTermMonths(loanResponseDTO.getLoanTerm());
        loanReport.setTotalPayments(loanResponseDTO.getTotalPayments());
        loanReport.setTotalInterest(loanResponseDTO.getTotalInterest());
        loanReport.setMonthlyLoanResponsesDTO(loanResponseDTO.getMonthlyLoanResponsesDTO());

        return loanReport;
    }
}
