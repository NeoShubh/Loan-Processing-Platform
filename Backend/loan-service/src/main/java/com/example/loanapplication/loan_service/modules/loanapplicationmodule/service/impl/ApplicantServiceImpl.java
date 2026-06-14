package com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.impl;

import com.example.loanapplication.loan_service.exception.applicant.ApplicantNotFoundException;
import com.example.loanapplication.loan_service.exception.applicant.PrimaryApplicantaExists;
import com.example.loanapplication.loan_service.external.services.DocumentService;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.applicantDTO.ApplicantRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.applicantDTO.ApplicantResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity.Applicant;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.ApplicantType;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.repository.ApplicantRepository;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.ApplicantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final DocumentService documentService;
    private static final Logger log =
            LoggerFactory.getLogger(ApplicantServiceImpl.class);

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, DocumentService documentService) {
        this.applicantRepository = applicantRepository;
        this.documentService = documentService;
    }

    @Override
    public ApplicantResponseDTO createApplicant(ApplicantRequestDTO applicantRequestDTO) {
        log.info("Creating applicant");
        UUID loanId = UUID.fromString(applicantRequestDTO.getLoanApplication());
        System.out.println(loanId);

        if (applicantRequestDTO.getApplicantType() == ApplicantType.PRIMARY) {

            Optional<Applicant> existingPrimary =
                    applicantRepository.findByLoanApplication_LoanIDAndApplicantType(
                            loanId, ApplicantType.PRIMARY);

            if (existingPrimary.isPresent()) {
                throw new PrimaryApplicantaExists("Primary applicant already exists for this loan");
            }
        }
        Applicant applicant = new Applicant();
        applicant.setLoanApplication(LoanApplication.builder().loanID(UUID.fromString(applicantRequestDTO.getLoanApplication())).build());
        applicant.setName(applicantRequestDTO.getName());
        applicant.setPanNumber(applicantRequestDTO.getPanNumber());
        applicant.setAddress(applicantRequestDTO.getAddress());
        applicant.setApplicantType(applicantRequestDTO.getApplicantType());
        Applicant savedApplicant = applicantRepository.save(applicant);

        return ApplicantResponseDTO.builder()
                .applicantId(savedApplicant.getApplicantId())
                .loanApplication(savedApplicant.getLoanApplication().getLoanID())
                .name(savedApplicant.getName())
                .panNumber(savedApplicant.getPanNumber())
                .address(savedApplicant.getAddress())
                .applicantType(savedApplicant.getApplicantType())
                .createdAt(savedApplicant.getCreatedAt())
                .build();
    }

    @Override
    public ApplicantResponseDTO updateApplicant(String ApplicantId, ApplicantRequestDTO applicantRequestDTO) {
        System.out.println(ApplicantId);
        Applicant applicant = applicantRepository.findById(UUID.fromString(ApplicantId)).orElseThrow(() -> new ApplicantNotFoundException("Applicant Not Found"));


        if (applicantRequestDTO.getLoanApplication() != null) {
            applicant.setLoanApplication(LoanApplication.builder().loanID(UUID.fromString(applicantRequestDTO.getLoanApplication())).build());
        }

        if (applicantRequestDTO.getApplicantType() != null) {
            applicant.setApplicantType(applicantRequestDTO.getApplicantType());
        }

        if (applicantRequestDTO.getName() != null) {
            applicant.setName(applicantRequestDTO.getName());
        }

        if (applicantRequestDTO.getPanNumber() != null) {
            applicant.setPanNumber(applicantRequestDTO.getPanNumber());
        }

        if (applicantRequestDTO.getAddress() != null) {
            applicant.setAddress(applicantRequestDTO.getAddress());
        }
        applicantRepository.save(applicant);

        return ApplicantResponseDTO.builder()
                .applicantId(applicant.getApplicantId())
                .loanApplication(applicant.getApplicantId())
                .applicantType(applicant.getApplicantType())
                .name(applicant.getName())
                .address(applicant.getAddress())
                .panNumber(applicant.getPanNumber())
                .createdAt(applicant.getCreatedAt()).build();
    }

    @Override
    public void deleteApplicantById(String ApplicantId) {
        Applicant applicant = applicantRepository.findById(UUID.fromString(ApplicantId)).orElseThrow(() -> new ApplicantNotFoundException("Applicant Not Found"));
//        documentService.deleteAllDocumentsByApplicantId(ApplicantId);
        applicantRepository.deleteById(applicant.getApplicantId());
    }

    @Transactional
    @Modifying
    @Override
    public void deleteAllApplicantByLoanId(String loanId) {
//        documentService.deleteAllDocumentsByLoanId(loanId);
        System.out.println(loanId);
        long count = applicantRepository.deleteAllByLoanApplicationLoanID(UUID.fromString(loanId));
        if (count == 0) {
            throw new ApplicantNotFoundException("No Applicants found for this loan application");
        }
    }

    @Override
    public ApplicantResponseDTO getApplicantById(String ApplicantId) {
        Applicant applicant = applicantRepository.findById(UUID.fromString(ApplicantId)).orElseThrow(() -> new ApplicantNotFoundException("Applicant Not Found"));

        return ApplicantResponseDTO.builder()
                .applicantId(applicant.getApplicantId())
                .loanApplication(applicant.getLoanApplication().getLoanID())
                .name(applicant.getName())
                .panNumber(applicant.getPanNumber())
                .address(applicant.getAddress())
                .applicantType(applicant.getApplicantType())
                .createdAt(applicant.getCreatedAt()).build();
    }

    @Override
    public ApplicantResponseDTO getPrimaryApplicant(String loanId) {

        Optional<Applicant> applicant = applicantRepository
                .findByLoanApplication_LoanIDAndApplicantType(UUID.fromString(loanId), ApplicantType.PRIMARY);

        Applicant result = applicant.orElseThrow(() ->
                new ApplicantNotFoundException("Primary Applicant Not Found.")
        );

        return ApplicantResponseDTO.builder()
                .applicantId(result.getApplicantId())
                .loanApplication(result.getLoanApplication().getLoanID())
                .name(result.getName())
                .panNumber(result.getPanNumber())
                .address(result.getAddress())
                .applicantType(result.getApplicantType())
                .createdAt(result.getCreatedAt())
                .build();
    }

    @Override
    public List<ApplicantResponseDTO> getAllSecondaryApplicant(String loanId) {

        List<Applicant> applicants = applicantRepository.findAllByLoanApplication_LoanIDAndApplicantType(UUID.fromString(loanId), ApplicantType.SECONDARY);
        List<ApplicantResponseDTO> responseApplicants = new ArrayList<>();
        for (int i = 0; i < applicants.size(); i++) {
            ApplicantResponseDTO applicantResponseDTO = ApplicantResponseDTO.builder()
                    .applicantId(applicants.get(i).getApplicantId())
                    .loanApplication(applicants.get(i).getLoanApplication().getLoanID())
                    .name(applicants.get(i).getName())
                    .panNumber(applicants.get(i).getPanNumber())
                    .address(applicants.get(i).getAddress())
                    .applicantType(applicants.get(i).getApplicantType())
                    .createdAt(applicants.get(i).getCreatedAt()).build();
            responseApplicants.add(applicantResponseDTO);
        }
        return responseApplicants;
    }

    @Override
    public List<ApplicantResponseDTO> getAllApplicant(String loanId) {
        List<Applicant> applicants = applicantRepository.findByLoanApplicationLoanID(UUID.fromString(loanId));
        List<ApplicantResponseDTO> responseApplicants = new ArrayList<>();
        for (int i = 0; i < applicants.size(); i++) {
            ApplicantResponseDTO applicantResponseDTO = ApplicantResponseDTO.builder()
                    .applicantId(applicants.get(i).getApplicantId())
                    .loanApplication(applicants.get(i).getLoanApplication().getLoanID())
                    .name(applicants.get(i).getName())
                    .panNumber(applicants.get(i).getPanNumber())
                    .address(applicants.get(i).getAddress())
                    .applicantType(applicants.get(i).getApplicantType())
                    .createdAt(applicants.get(i).getCreatedAt()).build();
            responseApplicants.add(applicantResponseDTO);
        }

        return responseApplicants;
    }
}
