package com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_stage_history")
public class LoanStageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID loanStageHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanApplication loanApplication;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_stage")
    private LoanStage oldStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_stage")
    private LoanStage currentStage;


    @Column(name = "changed_by", nullable = false, columnDefinition = "VARCHAR(36)")
    private String changedBy;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @PrePersist
    public void onCreate() {
        this.changedAt = LocalDateTime.now();
    }

}
