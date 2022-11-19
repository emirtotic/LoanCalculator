package com.loancalculator.unit.mapper;

import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.entity.LoanRequest;
import com.loancalculator.mapper.LoanMapper;
import com.loancalculator.mapper.LoanMapperImpl;
import com.loancalculator.mapper.MonthlyCalculationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoanMapperTest {

    private LoanMapper loanMapper;

    @BeforeEach
    void setUp() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor mapperConstructor =
                LoanMapperImpl.class.getConstructor(MonthlyCalculationMapper.class);
        loanMapper =
                (LoanMapper)
                        mapperConstructor.newInstance(Mappers.getMapper(MonthlyCalculationMapper.class));
    }

    @Test
    @DisplayName("Mapping LoanRequestDTO to LoanRequest Test")
    void mapToEntityTest() {

        LoanRequestDTO loanRequestDTO = createLoanRequestDTO();
        LoanRequest loanRequest = loanMapper.mapToEntity(loanRequestDTO);

        assertNotNull(loanRequest);
        assertEquals(loanRequestDTO.getLoanAmount(), loanRequest.getLoanAmount());
        assertEquals(loanRequestDTO.getInterestRate(), loanRequest.getInterestRate());
        assertEquals(loanRequestDTO.getLoanTermYears(), loanRequest.getLoanTermYears());
    }

    @Test
    @DisplayName("Mapping LoanRequest to LoanRequestDTO Test")
    void mapToDTO() {

        LoanRequest loanRequest = createLoanRequest();
        LoanRequestDTO loanRequestDTO = loanMapper.mapToDTO(loanRequest);

        assertNotNull(loanRequestDTO);
        assertEquals(loanRequest.getLoanAmount(), loanRequestDTO.getLoanAmount());
        assertEquals(loanRequest.getInterestRate(), loanRequestDTO.getInterestRate());
        assertEquals(loanRequest.getLoanTermYears(), loanRequestDTO.getLoanTermYears());
    }

    private LoanRequestDTO createLoanRequestDTO() {
        LoanRequestDTO loanRequestDTO = new LoanRequestDTO();
        loanRequestDTO.setLoanAmount(new BigDecimal(800.00));
        loanRequestDTO.setInterestRate(6.1);
        loanRequestDTO.setLoanTermYears(1);

        return loanRequestDTO;
    }

    private LoanRequest createLoanRequest() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setId(1L);
        loanRequest.setLoanAmount(new BigDecimal(800.00));
        loanRequest.setInterestRate(6.1);
        loanRequest.setLoanTermYears(1);

        return loanRequest;
    }

}