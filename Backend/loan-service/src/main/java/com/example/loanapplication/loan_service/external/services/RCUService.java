package com.example.loanapplication.loan_service.external.services;

import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.rcuDTO.RCUCaseResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("RCU-SERVICE")
public interface RCUService {
//    @PostMapping("/api/rcu/cases/{loanId}")
//    ResponseEntity<RCUCaseResponseDTO> CreateRCUCase(@PathVariable String loanId);
}
