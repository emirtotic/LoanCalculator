package com.loancalculator.repository;

import com.loancalculator.entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanCalculatorRepository extends JpaRepository<LoanRequest, Long> {

    @Query(value = "SELECT lr FROM LoanRequest lr LEFT JOIN FETCH lr.monthlyLoanResponses mlr WHERE mlr.loanRequest.id = :id")
    Optional<LoanRequest> findLoanRequest(@Param("id") Long id);
}