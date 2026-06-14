package com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity;

import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.ApplicantType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="applicants")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID applicantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanApplication loanApplication;

    private String name;
    private String panNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private ApplicantType applicantType;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
