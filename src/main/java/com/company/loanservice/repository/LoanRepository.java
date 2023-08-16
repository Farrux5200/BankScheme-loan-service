package com.company.loanservice.repository;

import com.company.loanservice.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    Optional<Loan> findByLoanIdAndDeletedAtIsNull(Integer loanId);

    @Query(value = "select lo from Loan as lo " +
            "where coalesce(:loanId, lo.loanId)=lo.loanId and " +
            "coalesce(:goal, lo.goal)=lo.goal and " +
            "coalesce(:issuedAmount, lo.issuedAmount)=lo.issuedAmount and " +
            "coalesce(:remainingAmount,lo.remainingAmount)=lo.remainingAmount ")
    Page<Loan> getSearch(@Param("loanId") Integer loanId,
                         @Param("goal") String goal,
                         @Param("issuedAmount") Double issuedAmount,
                         @Param("remainingAmount") Double remainingAmount,
                         Pageable pageable
    );
}
