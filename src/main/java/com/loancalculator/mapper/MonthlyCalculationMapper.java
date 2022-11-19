package com.loancalculator.mapper;

import com.loancalculator.dto.MonthlyLoanCalculationDTO;
import com.loancalculator.entity.MonthlyLoanCalculation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MonthlyCalculationMapper {

    MonthlyLoanCalculationDTO mapToResponse(MonthlyLoanCalculation monthlyLoanCalculation);

    List<MonthlyLoanCalculationDTO> mapToResponse(List<MonthlyLoanCalculation> monthlyLoanCalculations);

    MonthlyLoanCalculation mapToEntity(MonthlyLoanCalculationDTO monthlyLoanCalculationDTO);

    List<MonthlyLoanCalculation> mapToEntity(List<MonthlyLoanCalculationDTO> monthlyLoanCalculationsDTO);
}
