package com.company.loanservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanDto {
    private Integer loanId;
    private String goal;
    private Double issuedAmount;
    private Double remainingAmount;
    private Boolean status;

    private LoadPaymentDto loadPayment;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
