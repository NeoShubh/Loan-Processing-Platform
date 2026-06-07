package com.example.loanapplication.loan_service.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanStageChangedEvent {
    private UUID loanId;
    private String stage;
}
