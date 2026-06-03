package com.example.loanapplication.loan_service.modules.loanapplicationmodule.service;

import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.applicantDTO.ApplicantRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.applicantDTO.ApplicantResponseDTO;


import java.util.List;


public interface ApplicantService {

    ApplicantResponseDTO createApplicant(ApplicantRequestDTO applicantRequestDTO);
    ApplicantResponseDTO updateApplicant(String ApplicantId, ApplicantRequestDTO applicantRequestDTO);
    void deleteApplicantById(String ApplicantId);
    void deleteAllApplicantByLoanId(String loanId);
    ApplicantResponseDTO getApplicantById(String ApplicantId);
    ApplicantResponseDTO getPrimaryApplicant(String loanId);
    List<ApplicantResponseDTO> getAllSecondaryApplicant(String loanId);
    List<ApplicantResponseDTO> getAllApplicant(String loanId);
}
