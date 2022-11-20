package com.loancalculator.service.impl;

import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.dto.MonthlyLoanCalculationDTO;
import com.loancalculator.entity.LoanRequest;
import com.loancalculator.entity.MonthlyLoanCalculation;
import com.loancalculator.exception.CalculationNotFoundException;
import com.loancalculator.mapper.LoanMapper;
import com.loancalculator.mapper.MonthlyCalculationMapper;
import com.loancalculator.repository.LoanCalculatorRepository;
import com.loancalculator.service.LoanCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoanCalculatorServiceImpl implements LoanCalculatorService {

    private final LoanCalculatorRepository loanCalculatorRepository;
    private final LoanMapper loanMapper;
    private final MonthlyCalculationMapper monthlyCalculationMapper;

    @Override
    public LoanResponseDTO findById(long id) {

        log.info("Attempting to find Loan Request with id {}.", id);

        LoanRequest request = loanCalculatorRepository.findLoanRequest(id)
                .orElseThrow(() -> new CalculationNotFoundException(id));

        LoanResponseDTO response = loanMapper.mapToResponse(request);

        List<MonthlyLoanCalculation> calculationList = request.getMonthlyLoanResponses();
        List<MonthlyLoanCalculationDTO> calculationDTOList = new ArrayList<>();

        double sum = 0;
        for (MonthlyLoanCalculation calc : calculationList) {
            sum += calc.getPaymentAmount().setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            calculationDTOList.add(monthlyCalculationMapper.mapToResponse(calc));
        }

        response.setTotalPayments(new BigDecimal(sum).setScale(2, RoundingMode.HALF_EVEN));
        response.setLoanTerm(request.getLoanTermYears() * 12);
        response.setTotalInterest(new BigDecimal(
                response.getTotalPayments().doubleValue() - request.getLoanAmount().doubleValue())
                .setScale(2, RoundingMode.HALF_EVEN));
        response.setMonthlyLoanResponsesDTO(calculationDTOList);

        return response;
    }

    @Transactional
    @Override
    public LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO) {

        log.info("Creating Loan Request: {}...", loanRequestDTO);

        List<MonthlyLoanCalculation> calculationList = new ArrayList<>();

        LoanRequest loanRequest = loanMapper.mapToEntity(loanRequestDTO);

        int numberOfMonths = loanRequestDTO.getLoanTermYears() * 12;
        double i = loanRequestDTO.getInterestRate() / 100.0 / 12.0;
        double x = Math.pow(1 + i, numberOfMonths) * i;
        double divide = Math.pow(1 + i, numberOfMonths) - 1;

        BigDecimal payment = new BigDecimal(
                (loanRequestDTO.getLoanAmount().doubleValue() * x) / divide)
                .setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal balance = loanRequestDTO.getLoanAmount();

        BigDecimal totalInterest = BigDecimal.ZERO;
        BigDecimal totalPayment = BigDecimal.ZERO;
        int monthCounter = 1;

        for (int j = 0; j < numberOfMonths; j++) {

            MonthlyLoanCalculation calculation = new MonthlyLoanCalculation();
            calculation.setMonth(monthCounter++);
            calculation.setPaymentAmount(payment);
            calculation.setInterestAmount(balance.multiply(new BigDecimal(i))
                    .setScale(2, RoundingMode.HALF_EVEN));
            calculation.setBalanceOwed(balance.subtract(payment).add(calculation.getInterestAmount()));
            calculation.setPrincipalAmount(payment.subtract(calculation.getInterestAmount())
                    .setScale(2, RoundingMode.HALF_EVEN));
            calculation.setLoanRequest(loanRequest);

            calculationList.add(calculation);
            balance = calculation.getBalanceOwed();

            if (j == (numberOfMonths - 1)) {
                calculation.setPaymentAmount(payment.add(calculation.getBalanceOwed()));
                calculation.setBalanceOwed(new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN));
            }

            totalInterest = totalInterest.add(calculation.getInterestAmount());
            totalPayment = totalPayment.add(calculation.getPaymentAmount());
        }

        loanRequest.setMonthlyLoanResponses(calculationList);
        loanCalculatorRepository.save(loanRequest);

        LoanResponseDTO loanResponseDTO = loanMapper.mapToResponse(loanRequest);
        loanResponseDTO.setTotalPayments(loanRequestDTO.getLoanAmount().add(totalInterest));
        loanResponseDTO.setTotalInterest(totalPayment.subtract(loanRequestDTO.getLoanAmount()));
        loanResponseDTO.setLoanTerm(loanRequestDTO.getLoanTermYears() * 12);

        log.info("Loan is successfully created!");

        return loanResponseDTO;
    }

    @Override
    public void deleteLoan(long id) {

        log.warn("Attempting to delete Loan Request with id {}.", id);
        loanCalculatorRepository.findById(id).orElseThrow(() -> new CalculationNotFoundException(id));
        loanCalculatorRepository.deleteById(id);
        log.info("Loan Request with id {} has been deleted successfully.", id);
    }
}
