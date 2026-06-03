package com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity;

import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.CreditStatus;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanType;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.RCUStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_application")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loanID;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Enumerated(EnumType.STRING)
    private LoanStage loanStage;

    @Enumerated(EnumType.STRING)
    private RCUStatus rcuStatus;

    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    @Column(name = "created_by", nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
