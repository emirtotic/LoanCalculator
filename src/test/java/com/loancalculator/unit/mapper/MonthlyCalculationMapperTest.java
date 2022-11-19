package com.loancalculator.unit.mapper;

import com.loancalculator.dto.MonthlyLoanCalculationDTO;
import com.loancalculator.entity.MonthlyLoanCalculation;
import com.loancalculator.mapper.MonthlyCalculationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonthlyCalculationMapperTest {

    private MonthlyCalculationMapper monthlyCalculationMapper;
    private MonthlyLoanCalculation entity;
    private MonthlyLoanCalculationDTO dto;

    @BeforeEach
    void setUp() {
        monthlyCalculationMapper = Mappers.getMapper(MonthlyCalculationMapper.class);
        entity = createMonthlyLoanCalculation(1, new BigDecimal(68.89),
                new BigDecimal(64.82), new BigDecimal(4.07), new BigDecimal(735.18));
        dto = createMonthlyLoanCalculationDTO(1, new BigDecimal(68.89),
                new BigDecimal(64.82), new BigDecimal(4.07), new BigDecimal(735.18));
    }

    @Test
    @DisplayName("Mapping MonthlyLoanCalculation to MonthlyLoanCalculationDTO Test")
    void mapToResponseTest() {

        MonthlyLoanCalculationDTO dto = monthlyCalculationMapper.mapToResponse(entity);

        assertNotNull(dto);
        assertEquals(entity.getMonth(), dto.getMonth());
        assertEquals(entity.getPaymentAmount(), dto.getPaymentAmount());
        assertEquals(entity.getPrincipalAmount(), dto.getPrincipalAmount());
        assertEquals(entity.getInterestAmount(), dto.getInterestAmount());
        assertEquals(entity.getBalanceOwed(), dto.getBalanceOwed());
    }

    @Test
    @DisplayName("Mapping List<MonthlyLoanCalculation> to List<MonthlyLoanCalculationDTO> Test")
    void testMapToResponseTest() {

        List<MonthlyLoanCalculation> calculations = new ArrayList<>();
        calculations.add(entity);

        List<MonthlyLoanCalculationDTO> calculationsDTO = monthlyCalculationMapper.mapToResponse(calculations);

        assertNotNull(calculationsDTO);
        assertNotEquals(calculationsDTO.size(), 0);
        assertEquals(calculations.get(0).getMonth(), calculationsDTO.get(0).getMonth());
        assertEquals(calculations.get(0).getPaymentAmount(), calculationsDTO.get(0).getPaymentAmount());
        assertEquals(calculations.get(0).getPrincipalAmount(), calculationsDTO.get(0).getPrincipalAmount());
        assertEquals(calculations.get(0).getInterestAmount(), calculationsDTO.get(0).getInterestAmount());
        assertEquals(calculations.get(0).getBalanceOwed(), calculationsDTO.get(0).getBalanceOwed());
    }

    @Test
    @DisplayName("Mapping MonthlyLoanCalculationDTO to MonthlyLoanCalculation Test")
    void mapToEntityTest() {

        MonthlyLoanCalculation entity = monthlyCalculationMapper.mapToEntity(dto);

        assertNotNull(dto);
        assertEquals(dto.getMonth(), entity.getMonth());
        assertEquals(dto.getPaymentAmount(), entity.getPaymentAmount());
        assertEquals(dto.getPrincipalAmount(), entity.getPrincipalAmount());
        assertEquals(dto.getInterestAmount(), entity.getInterestAmount());
        assertEquals(dto.getBalanceOwed(), entity.getBalanceOwed());
    }

    @Test
    @DisplayName("Mapping List<MonthlyLoanCalculationDTO> to List<MonthlyLoanCalculation> Test")
    void testMapToEntityTest() {

        List<MonthlyLoanCalculationDTO> calculationsDTO = new ArrayList<>();
        calculationsDTO.add(dto);

        List<MonthlyLoanCalculation> calculations = monthlyCalculationMapper.mapToEntity(calculationsDTO);

        assertNotNull(calculationsDTO);
        assertNotEquals(calculationsDTO.size(), 0);
        assertEquals(calculationsDTO.get(0).getMonth(), calculations.get(0).getMonth());
        assertEquals(calculationsDTO.get(0).getPaymentAmount(), calculations.get(0).getPaymentAmount());
        assertEquals(calculationsDTO.get(0).getPrincipalAmount(), calculations.get(0).getPrincipalAmount());
        assertEquals(calculationsDTO.get(0).getInterestAmount(), calculations.get(0).getInterestAmount());
        assertEquals(calculationsDTO.get(0).getBalanceOwed(), calculations.get(0).getBalanceOwed());
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