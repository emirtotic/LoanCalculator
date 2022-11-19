package com.loancalculator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loancalculator.controller.LoanController;
import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.dto.MonthlyLoanCalculationDTO;
import com.loancalculator.entity.LoanRequest;
import com.loancalculator.repository.LoanCalculatorRepository;
import com.loancalculator.service.LoanCalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class LoanServiceTest {

    @Autowired
    public LoanController loanController;
    @Autowired
    public LoanCalculatorService loanCalculatorService;
    @Autowired
    public LoanCalculatorRepository repository;
    public MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    public LoanRequest request;
    public LoanResponseDTO responseDTO;
    public LoanRequestDTO requestDTO;
    public LoanRequest existingRequest;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
        responseDTO = createLoanResponseDTO();
        requestDTO = createLoanRequestDTO();
        request = createLoanRequest();
        existingRequest = repository.save(request);
    }

    @Test
    @DisplayName("Find Loan Calculation by ID")
    void findLoanById() throws Exception {

        try {

            Optional<LoanRequest> saved = repository.findById(existingRequest.getId());

            mockMvc.perform(get("/api/loan-calculator/2")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(responseDTO))
            ).andExpect(status().is2xxSuccessful());

            Assertions.assertEquals(saved.get().getId(), existingRequest.getId());
            Assertions.assertEquals(saved.get().getLoanAmount().doubleValue(), existingRequest.getLoanAmount().doubleValue());
            Assertions.assertEquals(saved.get().getLoanTermYears(), existingRequest.getLoanTermYears());
            Assertions.assertEquals(saved.get().getInterestRate(), existingRequest.getInterestRate());
        } finally {
            loanCalculatorService.deleteLoan(existingRequest.getId());
        }
    }

    @Test
    @DisplayName("Create Loan Calculation")
    @Transactional
    void createLoan() throws Exception {

        LoanRequest retrieved = null;

        try {

            MvcResult result = mockMvc.perform(post("/api/loan-calculator/create")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(requestDTO))
            ).andReturn();

            LoanResponseDTO res = objectMapper.readValue(result.getResponse().getContentAsString(), LoanResponseDTO.class);
            retrieved = repository.findById(res.getId()).orElseThrow();
            Assertions.assertEquals(res.getId(), retrieved.getId());
            Assertions.assertTrue(res.getLoanAmount().compareTo(retrieved.getLoanAmount()) == 0);
            Assertions.assertEquals(res.getInterestRate(), retrieved.getInterestRate());
            Assertions.assertEquals(res.getLoanTerm(), retrieved.getLoanTermYears() * 12);
            Assertions.assertEquals(res.getMonthlyLoanResponsesDTO().size(), retrieved.getMonthlyLoanResponses().size());
        } finally {
            loanCalculatorService.deleteLoan(existingRequest.getId());
            loanCalculatorService.deleteLoan(retrieved.getId());
        }
    }

    @Test
    @DisplayName("Delete Loan Calculation by ID")
    void deleteLoan() throws Exception {

        mockMvc.perform(delete("/api/loan-calculator/" + existingRequest.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestDTO))
        ).andExpect(status().is2xxSuccessful());

        Optional<LoanRequest> retrieved = repository.findById(existingRequest.getId());

        Assertions.assertTrue(retrieved.isEmpty());
    }

    private LoanRequestDTO createLoanRequestDTO() {

        LoanRequestDTO loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setLoanAmount(new BigDecimal(1000.00));
        loanRequestDTO.setInterestRate(3.7);
        loanRequestDTO.setLoanTermYears(1);

        return loanRequestDTO;
    }

    private LoanResponseDTO createLoanResponseDTO() {

        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setLoanAmount(new BigDecimal(1000.00));
        loanResponseDTO.setInterestRate(3.7);
        loanResponseDTO.setLoanTerm(1);
        loanResponseDTO.setTotalPayments(new BigDecimal(1020.15));
        loanResponseDTO.setTotalInterest(new BigDecimal(20.15));

        List<MonthlyLoanCalculationDTO> calculationDTOList = new ArrayList<>();
        calculationDTOList.add(createMonthlyLoanCalculationDTO(1, new BigDecimal(85.01),
                new BigDecimal(81.93), new BigDecimal(3.08), new BigDecimal(918.07)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(2, new BigDecimal(85.01),
                new BigDecimal(82.18), new BigDecimal(2.83), new BigDecimal(835.89)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(3, new BigDecimal(85.01),
                new BigDecimal(82.43), new BigDecimal(2.58), new BigDecimal(753.46)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(4, new BigDecimal(85.01),
                new BigDecimal(82.69), new BigDecimal(2.32), new BigDecimal(670.77)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(5, new BigDecimal(85.01),
                new BigDecimal(82.94), new BigDecimal(2.07), new BigDecimal(587.83)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(6, new BigDecimal(85.01),
                new BigDecimal(83.20), new BigDecimal(1.81), new BigDecimal(504.63)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(7, new BigDecimal(85.01),
                new BigDecimal(83.45), new BigDecimal(1.56), new BigDecimal(421.18)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(8, new BigDecimal(85.01),
                new BigDecimal(83.71), new BigDecimal(1.30), new BigDecimal(337.47)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(9, new BigDecimal(85.01),
                new BigDecimal(83.97), new BigDecimal(1.04), new BigDecimal(253.50)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(10, new BigDecimal(85.01),
                new BigDecimal(84.23), new BigDecimal(0.78), new BigDecimal(169.27)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(11, new BigDecimal(85.01),
                new BigDecimal(84.49), new BigDecimal(0.52), new BigDecimal(84.78)));
        calculationDTOList.add(createMonthlyLoanCalculationDTO(12, new BigDecimal(85.01),
                new BigDecimal(84.75), new BigDecimal(0.26), new BigDecimal(0.00)));

        loanResponseDTO.setMonthlyLoanResponsesDTO(calculationDTOList);
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

    private LoanRequest createLoanRequest() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setId(100L);
        loanRequest.setLoanAmount(new BigDecimal(1000.00));
        loanRequest.setInterestRate(3.7);
        loanRequest.setLoanTermYears(1);

        return loanRequest;
    }
}
