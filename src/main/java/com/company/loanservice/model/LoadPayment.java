package com.company.loanservice.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ("load_payment"))
public class LoadPayment {              // To'lovni yuklash=> credit to'lovi
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "load_payment_id")
    private Integer loadPaymentId;
    private Double amount;
    private String description;
    private Boolean status;

    @Column(name = "loan_id")
    private Integer loanId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
