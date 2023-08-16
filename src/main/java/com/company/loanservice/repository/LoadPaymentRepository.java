package com.company.loanservice.repository;

import com.company.loanservice.model.LoadPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoadPaymentRepository extends JpaRepository<LoadPayment, Integer> {

    Optional<LoadPayment> findByLoadPaymentIdAndDeletedAtIsNull(Integer loadId);

    @Query(value = "select l from LoadPayment as l " +
            "where coalesce(:loadPaymentId,l.loadPaymentId)=l.loadPaymentId and " +
            "coalesce(:amount,l.amount)=l.amount and " +
            "coalesce(:description,l.description)=l.description and " +
            "coalesce(:loanId,l.loanId)=l.loanId ")
    Page<LoadPayment> getSearch(@Param("loadPaymentId") Integer loadPaymentId,
                                @Param("amount") Double amount,
                                @Param("description") String description,
                                @Param("loanId") Integer loanId,
                                Pageable pageable
                                );

}
