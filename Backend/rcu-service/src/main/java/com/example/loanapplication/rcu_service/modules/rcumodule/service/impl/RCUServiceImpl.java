package com.example.loanapplication.rcu_service.modules.rcumodule.service.impl;


import com.example.loanapplication.rcu_service.exception.rcuCase.ActiveRCUCaseFoundException;
import com.example.loanapplication.rcu_service.exception.rcuCase.RCUCaseIsNotAssignedException;
import com.example.loanapplication.rcu_service.exception.rcuCase.RCUCaseNotPresentException;
import com.example.loanapplication.rcu_service.exception.rcuCase.RCUStatusCanNotBeChangedException;
import com.example.loanapplication.rcu_service.modules.rcumodule.dto.standardDTOs.RCUCaseResponseDTO;
import com.example.loanapplication.rcu_service.modules.rcumodule.entity.RCUCase;
import com.example.loanapplication.rcu_service.modules.rcumodule.enums.RCUStatus;
import com.example.loanapplication.rcu_service.modules.rcumodule.repository.RCUCaseRepository;
import com.example.loanapplication.rcu_service.modules.rcumodule.service.RCUService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RCUServiceImpl implements RCUService {

    private final RCUCaseRepository rcuCaseRepository;
//    private final DocumentService documentService;

//    public RCUServiceImpl(RCUCaseRepository rcuCaseRepository, DocumentService documentService) {
//        this.rcuCaseRepository = rcuCaseRepository;
//        this.documentService = documentService;
//    }

public RCUServiceImpl(RCUCaseRepository rcuCaseRepository) {
    this.rcuCaseRepository = rcuCaseRepository;
}

    @Override
    public RCUCaseResponseDTO CreateRCUCase(UUID loanId) {
        if (CheckRCUCaseExistsForLoanId(loanId, RCUStatus.PENDING)) {
            return null; // skip duplicate
        }
        if (CheckRCUCaseExistsForLoanId(loanId, RCUStatus.PENDING)) {
            throw new ActiveRCUCaseFoundException("The current RCU Case should be closed with either 'Approved' or 'Rejected'");
        }
        // 👉 Idempotency check FIRST
        if (CheckRCUCaseExistsForLoanId(loanId, RCUStatus.PENDING)) {
            return null; // or just return existing / skip
        }

        RCUCase rcuCase = new RCUCase();

        rcuCase.setLoanId(loanId);
        rcuCase.setRcuStatus(RCUStatus.PENDING);
        rcuCase.setAssignedTo(null);

        rcuCaseRepository.save(rcuCase);
        return RCUCaseResponseDTO.builder()
                .rcuCaseId(rcuCase.getRcuCaseId())
                .loan(rcuCase.getLoanId())
                .rcuStatus(rcuCase.getRcuStatus())
                .assignedTo(rcuCase.getAssignedTo() != null ? rcuCase.getAssignedTo() : null)
                .updatedAt(rcuCase.getUpdatedAt())
                .createdAt(rcuCase.getCreatedAt())
                .closedAt(rcuCase.getClosedAt())
                .build();
    }

    @Override
    public void DeleteRCUCaseById(UUID rcuCaseId) {
        RCUCase rcuCase = rcuCaseRepository.findById(rcuCaseId).orElseThrow(() -> new RCUCaseNotPresentException("RCU case is not present"));
        rcuCaseRepository.delete(rcuCase);
    }

    @Override
    public RCUCaseResponseDTO getRCUCase(UUID rcuCaseId) {
        RCUCase rcuCase = rcuCaseRepository.findById(rcuCaseId).orElseThrow(() -> new RCUCaseNotPresentException("RCU case is not present"));
        System.out.println("RCU ID: " + rcuCaseId);
        return RCUCaseResponseDTO.builder()
                .rcuCaseId(rcuCase.getRcuCaseId())
                .loan(rcuCase.getLoanId())
                .rcuStatus(rcuCase.getRcuStatus())
                .assignedTo(rcuCase.getAssignedTo() != null ? rcuCase.getAssignedTo() : null)
                .updatedAt(rcuCase.getUpdatedAt())
                .createdAt(rcuCase.getCreatedAt())
                .closedAt(rcuCase.getClosedAt())
                .build();
    }

    @Override
    public RCUCaseResponseDTO getRCUCaseByLoanID(UUID loanID) {

        RCUCase rcuCase = rcuCaseRepository
                .findByLoanId(loanID)
                .orElseThrow(() -> new RCUCaseNotPresentException("RCU Case Not Found"));

        return RCUCaseResponseDTO.builder()
                .rcuCaseId(rcuCase.getRcuCaseId())
                .loan(rcuCase.getLoanId())
                .rcuStatus(rcuCase.getRcuStatus())
                .assignedTo(rcuCase.getAssignedTo() != null ?
                        rcuCase.getAssignedTo(): null)
                .updatedAt(rcuCase.getUpdatedAt())
                .createdAt(rcuCase.getCreatedAt())
                .closedAt(rcuCase.getClosedAt())
                .build();
    }

//
//    @Override
//    public DocumentResponseDTO updateDocumentStatusAndRemarks(String documentId, DocumentStatusRequestDTO documentStatusRequestDTO) {
//        return documentService.updateDocumentStatus(documentId, documentStatusRequestDTO);
//    }
//
//    @Override
//    public DocumentResponseDTO getDocument(String documentId) {
//        return documentService.getDocumentById(UUID.fromString(documentId));
//    }
//
//    @Override
//    public List<DocumentResponseDTO> getAllDocumentByApplicant(String applicantId) {
//        return documentService.getAllDocumentsByApplicantId(UUID.fromString(applicantId));
//    }

//    @Override
//    public List<DocumentResponseDTO> getAllDOcumentByLoanId(String loanId) {
//        List<Document> documents = documentService.getDocumentsByLoanId(UUID.fromString(loanId));
//        List<DocumentResponseDTO> documentResponseList = new ArrayList<>();
//
//        for (int i = 0; i < documents.size(); i++) {
//            DocumentResponseDTO singleDocumentResponseDTO = DocumentResponseDTO.builder()
//                    .documentId(documents.get(i).getDocumentId())
//                    .loanApplication(documents.get(i).getLoanApplication().getLoanID())
//                    .applicant(documents.get(i).getApplicant() != null ?
//                            documents.get(i).getApplicant().getApplicantId() : null)
//                    .documentStatus(documents.get(i).getDocumentStatus())
//                    .documentType(documents.get(i).getDocumentType())
//                    .fileUrl(documents.get(i).getFileUrl())
//                    .uploadedBy(documents.get(i).getUploadedBy().getUserID())
//                    .verifiedBy(documents.get(i).getVerifiedBy() != null ? documents.get(i).getVerifiedBy().getUserID() : null)
//                    .verifiedAt(documents.get(i).getVerifiedAt())
//                    .uploadedAt(documents.get(i).getUploadedAt())
//                    .updatedAt(documents.get(i).getUpdatedAt())
//                    .remarks(documents.get(i).getRemarks() != null ? documents.get(i).getRemarks() : null).build();
//
//            documentResponseList.add(singleDocumentResponseDTO);
//        }
//        return documentResponseList;
//    }

    @Override
    public RCUCaseResponseDTO updateRCUCaseStatus(UUID rcuCaseId, RCUStatus rcuStatus) {
        RCUCase rcuCase = rcuCaseRepository.findById(rcuCaseId).orElseThrow(() ->
                new RCUCaseNotPresentException("RCU Case Not Found"));

        if (rcuCase.getAssignedTo() == null)
            throw new RCUCaseIsNotAssignedException("The RCU case is not assigned yet.");

        if (rcuCase.getRcuStatus().equals(RCUStatus.REJECTED))
            throw new RCUStatusCanNotBeChangedException("The RCU case is already 'rejected'. You can not make changes in RCU Status.");

        if (rcuCase.getRcuStatus().equals(RCUStatus.APPROVED))
            throw new RCUStatusCanNotBeChangedException("The RCU case is already 'approved'. You can not make changes in RCU Status.");

        if (rcuCase.getRcuStatus().equals(RCUStatus.PENDING) &&
                (rcuStatus.equals(RCUStatus.REJECTED) || rcuStatus.equals(RCUStatus.APPROVED))) {
            rcuCase.setRcuStatus(rcuStatus);
            rcuCase.setClosedAt(LocalDateTime.now());
        }
        rcuCaseRepository.save(rcuCase);

        return RCUCaseResponseDTO.builder()
                .rcuCaseId(rcuCaseId)
                .loan(rcuCase.getLoanId())
                .rcuStatus(rcuCase.getRcuStatus())
                .assignedTo(rcuCase.getAssignedTo())
                .updatedAt(rcuCase.getUpdatedAt())
                .createdAt(rcuCase.getCreatedAt())
                .closedAt(rcuCase.getClosedAt())
                .build();
    }

    @Override
    public RCUCaseResponseDTO AssignedRCUCase(UUID rcuCaseId, UUID assignedUser) {
        RCUCase rcuCase = rcuCaseRepository.findById(rcuCaseId).orElseThrow(() ->
                new RCUCaseNotPresentException("RCU Case Not Found"));

        rcuCase.setAssignedTo(assignedUser);
        rcuCaseRepository.save(rcuCase);
        return RCUCaseResponseDTO.builder()
                .rcuCaseId(rcuCaseId)
                .loan(rcuCase.getLoanId())
                .rcuStatus(rcuCase.getRcuStatus())
                .assignedTo(rcuCase.getAssignedTo())
                .updatedAt(rcuCase.getUpdatedAt())
                .createdAt(rcuCase.getCreatedAt())
                .closedAt(rcuCase.getClosedAt())
                .build();
    }
//
//    @Override
//    public void RCUCaseDecisionMaking(UUID rcuCaseId) {
//        RCUCase rcuCase = rcuCaseRepository.findById(rcuCaseId).orElseThrow(() ->
//                new RCUCaseNotPresentException("RCU Case Not Found"));
//
//        if (rcuCase.getAssignedTo() == null) {
//            throw new RCUStatusCanNotBeChangedException("RCU User not assigned");
//        }
//        System.out.println("Loan ID in RCU : " + rcuCase.getLoan().getLoanID());
//        List<Document> documentList = documentService.getDocumentsByLoanId(rcuCase.getLoan().getLoanID());
//        System.out.print(documentList);
//        if (documentList.isEmpty()) {
//            throw new DocumentNotFoundException("Document List is not Found");
//        }
//        boolean anyRejected = documentList.stream()
//                .anyMatch(doc -> doc.getDocumentStatus() == DocumentStatus.REJECTED);
//
//        boolean allApproved = documentList.stream()
//                .allMatch(doc -> doc.getDocumentStatus() == DocumentStatus.VERIFIED);
//
//        if (anyRejected)
//            updateRCUCaseStatus(rcuCase.getRcuCaseId(), RCUStatus.REJECTED);
//        else if (allApproved && !documentList.isEmpty())
//            updateRCUCaseStatus(rcuCase.getRcuCaseId(), RCUStatus.APPROVED);
//    }

    @Override
    public boolean CheckRCUCaseExistsForLoanId(UUID loanId, RCUStatus rcuStatus) {
        return rcuCaseRepository.existsByLoanIdAndRcuStatus(loanId, rcuStatus);
    }


}
