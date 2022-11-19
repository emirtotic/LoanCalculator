package com.loancalculator.controller;

import com.loancalculator.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan-calculator/report")
@Validated
@Tag(name = "Reports")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Create PDF Report", description = "Create Loan Calculation PDF Report")
    @GetMapping("/pdf/{loanId}")
    public ResponseEntity<String> createGeneralPdfLoanReport(@PathVariable(name = "loanId") Long id, final HttpServletResponse response) throws IOException {

        InputStream inputStream = reportService.createGeneralPdfReport(id);

        if (inputStream != null) {
            String generatedFileName = "LoanReport.pdf";
            response.setContentType(URLConnection.guessContentTypeFromName(generatedFileName));
            response.setHeader("Content-Disposition", "attachment; filename=" + generatedFileName);

            try {
                final ServletOutputStream outputStream = response.getOutputStream();
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create XLSX Report", description = "Create Loan Calculation Excel Report")
    @GetMapping("/excel/{loanId}")
    public ResponseEntity<String> createGeneralExcelLoanReport(@PathVariable(name = "loanId") Long id, final HttpServletResponse response) throws IOException {

        InputStream inputStream = reportService.createGeneralExcelReport(id);

        if (inputStream != null) {
            String generatedFileName = "LoanReport.xlsx";
            response.setContentType(URLConnection.guessContentTypeFromName(generatedFileName));
            response.setHeader("Content-Disposition", "attachment; filename=" + generatedFileName);

            try {
                final ServletOutputStream outputStream = response.getOutputStream();
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
