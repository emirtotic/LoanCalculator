package com.loancalculator.controller;

import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.service.LoanCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/loan-calculator", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Loan Calculator")
public class LoanController {

    private final LoanCalculatorService loanCalculatorService;

    @Operation(summary = "Create Loan Request", description = """
            <h2>Creating New Loan Calculation Request</h2>
            <p>Loan Request example:</p>
            <pre><code>{
              "loanAmount": 30000.00,
              "interestRate": 4.6,
              "loanTermYears": 15
            }
            </code></pre>
            """)
    @PostMapping("/create")
    public ResponseEntity<LoanResponseDTO> create(@RequestBody @Valid LoanRequestDTO loanRequestDTO) {
        return new ResponseEntity<>(loanCalculatorService.createLoan(loanRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Find Loan Calculation Request", description = """
            <h2>Finding Loan Calculation Request from Database by ID</h2>
            """)
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> findById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(loanCalculatorService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete Loan Calculation", description = """
            <h2>Deleting Loan Calculation Request from Database by ID</h2>
            """)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") Long id) {
        loanCalculatorService.deleteLoan(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
