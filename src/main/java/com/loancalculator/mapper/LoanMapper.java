package com.loancalculator.mapper;

import com.loancalculator.dto.LoanRequestDTO;
import com.loancalculator.dto.LoanResponseDTO;
import com.loancalculator.entity.LoanRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MonthlyCalculationMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LoanMapper {

    LoanRequest mapToEntity(LoanRequestDTO loanRequestDTO);

    LoanRequestDTO mapToDTO(LoanRequest loanRequest);

    @Mapping(target = "loanAmount", source = "loanRequest.loanAmount")
    @Mapping(target = "monthlyLoanResponsesDTO", source = "monthlyLoanResponses")
    LoanResponseDTO mapToResponse(LoanRequest loanRequest);

}
