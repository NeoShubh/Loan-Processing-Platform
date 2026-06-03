package com.example.loanapplication.rcu_service.modules.rcumodule.entity;

import com.example.loanapplication.rcu_service.modules.rcumodule.enums.RCUStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "rcu_cases")
public class RCUCase {

    @Id
    private UUID rcuCaseId;

    private UUID loanId;

    private RCUStatus rcuStatus;

    private UUID assignedTo;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime closedAt;
}
