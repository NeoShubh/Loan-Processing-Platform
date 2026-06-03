package com.example.loanapplication.loan_service.modules.loanapplicationmodule.controller;


import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    //Loan Application
    @PreAuthorize("hasAnyRole('RM')")
    @PostMapping("")
    ResponseEntity<LoanApplicationResponseDTO> createLoanApplication(@RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LoanApplicationResponseDTO loanApplicationResponseDTO = loanApplicationService.createLoanApplication(loanApplicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanApplicationResponseDTO);
    }

    @PreAuthorize("hasAnyRole('RM')")
    @GetMapping("/user/{userId}")
    ResponseEntity<List<LoanApplicationResponseDTO>> getAllLoanApplicationByUserID(@PathVariable String userId) {
        List<LoanApplicationResponseDTO> responseList = loanApplicationService.getAllLoanApplicationByUserID(userId);
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("hasAnyRole('RM')")
    @GetMapping("/{loanId}")
    ResponseEntity<LoanApplicationResponseDTO> getLoanApplicationById(@PathVariable String loanId) {
        LoanApplicationResponseDTO loanApplicationResponseDTO = loanApplicationService.getLoanApplicationById(loanId);
        return ResponseEntity.ok(loanApplicationResponseDTO);
    }

    @PreAuthorize("hasAnyRole('RM')")
    @PutMapping("/{loanId}")
    ResponseEntity<LoanApplicationResponseDTO> updateLoanApplication(@PathVariable String loanId, @RequestBody  LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LoanApplicationResponseDTO loanApplicationResponseDTO = loanApplicationService.updateLoanApplication(loanId, loanApplicationRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loanApplicationResponseDTO);
    }

    @PreAuthorize("hasAnyRole('RM')")
    @PutMapping("/{loanId}/stage")
    ResponseEntity<LoanApplicationResponseDTO> updateLoanApplicationStage(@PathVariable String loanId, @RequestParam  String loanStage) {
      LoanApplicationResponseDTO loanApplicationResponseDTO =  loanApplicationService.updateLoanApplicationStage(loanId, LoanStage.valueOf(loanStage));
        return ResponseEntity.status(HttpStatus.OK).body(loanApplicationResponseDTO);
    }

    @PreAuthorize("hasAnyRole('RM')")
    @DeleteMapping("/{loanId}")
    ResponseEntity<String> deleteLoanApplication(@PathVariable String loanId) {
        loanApplicationService.deleteLoanApplication(loanId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted Successfully");
    }

    @PreAuthorize("hasAnyRole('RM')")
    @GetMapping("/{loanId}/exists")
    ResponseEntity<String> isLoanApplicationExists(@PathVariable String loanId) {
        boolean flag = loanApplicationService.isLoanApplicationExists(loanId);
        if (flag)
            return ResponseEntity.status(HttpStatus.FOUND).body("Loan Application Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan Application Not Found");
    }

    //Loan Stage History
    @PreAuthorize("hasAnyRole('RM','CM', 'RCU')")
    @PostMapping("/{loanId}/{userId}/users/history")
    ResponseEntity<LoanStageHistoryResponseDTO> createLoanStageHistory(@PathVariable String loanId, @PathVariable String userId,@Valid @RequestBody LoanStageHistoryRequestDTO loanStageHistoryRequestDTO) {
        LoanStageHistoryResponseDTO loanStageHistoryResponseDTO = loanApplicationService.createLoanStageHistory(loanId, userId, loanStageHistoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanStageHistoryResponseDTO);
    }
    @PreAuthorize("hasAnyRole('RM','CM', 'RCU')")
    @GetMapping("/{loanId}/history")
    ResponseEntity<List<LoanStageHistoryResponseDTO>> getAllLoanStageHistoryByLoanId(@PathVariable String loanId) {
        List<LoanStageHistoryResponseDTO> loanStageHistoryResponseDTOlist = loanApplicationService.getAllLoanStageHistoryByLoanId(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(loanStageHistoryResponseDTOlist);
    }
    @PreAuthorize("hasAnyRole('RM','CM', 'RCU')")
    @GetMapping("/history/{loanStageHistoryId}")
    ResponseEntity<LoanStageHistoryResponseDTO> getLoanStageHistoryById(@PathVariable String loanStageHistoryId) {
        LoanStageHistoryResponseDTO loanStageHistoryResponseDTO = loanApplicationService.getLoanStageHistoryById(loanStageHistoryId);
        return ResponseEntity.ok(loanStageHistoryResponseDTO);
    }
    @PreAuthorize("hasAnyRole('RM','CM', 'RCU')")
    @DeleteMapping("/history/{loanStageHistoryId}")
    ResponseEntity<String> deleteLoanStageHistoryById(@PathVariable String loanStageHistoryId) {
        loanApplicationService.deleteLoanStageHistoryById(loanStageHistoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("content deleted");
    }
    @PreAuthorize("hasAnyRole('RM','CM', 'RCU')")
    @DeleteMapping("/{loanId}/history")
    ResponseEntity<String> deleteAllLoanStageHistoryByLoanId(@PathVariable String loanId) {
        loanApplicationService.deleteAllLoanStageHistoryByLoanId(loanId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("content deleted");
    }
    @PreAuthorize("hasAnyRole('RM','CM', 'RCU')")
    @PutMapping("/history/{loanStageHistoryId}")
    ResponseEntity<LoanStageHistoryResponseDTO> updateLoanStageHistory(@PathVariable String loanStageHistoryId, @RequestBody LoanStageHistoryRequestDTO loanStageHistoryRequestDTO) {
        LoanStageHistoryResponseDTO loanStageHistoryResponseDTO = loanApplicationService.updateLoanStageHistory(loanStageHistoryId, loanStageHistoryRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loanStageHistoryResponseDTO);
    }
}
