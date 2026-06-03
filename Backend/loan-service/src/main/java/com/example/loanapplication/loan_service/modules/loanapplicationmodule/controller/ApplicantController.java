package com.example.loanapplication.loan_service.modules.loanapplicationmodule.controller;

//import com.example.loanapplication.modules.loanapplicationmodule.dto.applicantDTO.ApplicantRequestDTO;
//import com.example.loanapplication.modules.loanapplicationmodule.dto.applicantDTO.ApplicantResponseDTO;
//import com.example.loanapplication.modules.loanapplicationmodule.service.ApplicantService;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.applicantDTO.ApplicantRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.applicantDTO.ApplicantResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.ApplicantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }
    @PreAuthorize("hasRole('RM')")
    @PostMapping("/applicants")
    ResponseEntity<ApplicantResponseDTO> createApplicant(@Valid @RequestBody ApplicantRequestDTO applicantRequestDTO){
        ApplicantResponseDTO applicantResponseDTO = applicantService.createApplicant(applicantRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicantResponseDTO);
    }
    @PreAuthorize("hasRole('RM')")
    @PutMapping("/applicants/{ApplicantId}")
    ResponseEntity<ApplicantResponseDTO> updateApplicant(@PathVariable  String ApplicantId,@RequestBody ApplicantRequestDTO applicantRequestDTO){
    ApplicantResponseDTO applicantResponseDTO = applicantService.updateApplicant(ApplicantId,applicantRequestDTO);
    return ResponseEntity.status(HttpStatus.OK).body(applicantResponseDTO);
    }
    @PreAuthorize("hasRole('RM')")
    @DeleteMapping("/applicants/{ApplicantId}")
    ResponseEntity<String> deleteApplicantById(@PathVariable String ApplicantId){
        applicantService.deleteApplicantById(ApplicantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("applicant deleted");
    }
    @PreAuthorize("hasRole('RM')")
    @DeleteMapping("/{LoanId}/applicants")
    ResponseEntity<String> deleteAllApplicantByLoanId(@PathVariable String LoanId){
        applicantService.deleteAllApplicantByLoanId(LoanId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("applicants deleted successfully");
    }
    @PreAuthorize("hasRole('RM')")
    @GetMapping("/applicants/{ApplicantId}")
    ResponseEntity<ApplicantResponseDTO> getApplicantById(@PathVariable  String ApplicantId){
       ApplicantResponseDTO applicantResponseDTO =  applicantService.getApplicantById(ApplicantId);
        return ResponseEntity.status(HttpStatus.OK).body(applicantResponseDTO);
    }
    @PreAuthorize("hasRole('RM')")
    @GetMapping("/{loanId}/applicants/primary")
    ResponseEntity<ApplicantResponseDTO>getPrimaryApplicant(@PathVariable String loanId){
        ApplicantResponseDTO applicantResponseDTO = applicantService.getPrimaryApplicant(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(applicantResponseDTO);
    }
    @PreAuthorize("hasRole('RM')")
    @GetMapping("/{loanId}/applicants/secondary")
    ResponseEntity<List<ApplicantResponseDTO>>getAllSecondaryApplicant(@PathVariable String loanId){
        List <ApplicantResponseDTO> responseDTOList = applicantService.getAllSecondaryApplicant(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }
    @PreAuthorize("hasRole('RM')")
    @GetMapping("/{loanId}/applicants")
    ResponseEntity<List<ApplicantResponseDTO>>getAllApplicant(@PathVariable String loanId){
        List <ApplicantResponseDTO> responseDTOList = applicantService.getAllApplicant(loanId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }
}
