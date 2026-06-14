package com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.impl;

import com.example.loanapplication.loan_service.exception.loanapplication.LoanApplicationNotFoundException;
import com.example.loanapplication.loan_service.exception.loanapplication.LoanStageHistoryNotFoundException;
import com.example.loanapplication.loan_service.exception.loanapplication.LoanStageTransitionNotAllowedException;
import com.example.loanapplication.loan_service.external.services.DocumentService;
import com.example.loanapplication.loan_service.kafka.events.LoanStageChangedEvent;
import com.example.loanapplication.loan_service.kafka.events.LoanStageHistoryEvent;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity.LoanApplication;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.entity.LoanStageHistory;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.CreditStatus;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanType;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.RCUStatus;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.repository.LoanApplicationRepository;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.repository.LoanStageHistoryRepository;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.ApplicantService;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.service.LoanApplicationService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanStageHistoryRepository loanStageHistoryRepository;
    private final ApplicantService applicantService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final DocumentService documentService;

    public LoanApplicationServiceImpl(
            LoanApplicationRepository loanApplicationRepository,
            LoanStageHistoryRepository loanStageHistoryRepository,

            ApplicantService applicantService, KafkaTemplate<String, Object> kafkaTemplate, DocumentService documentService
    ) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanStageHistoryRepository = loanStageHistoryRepository;
        this.applicantService = applicantService;
        this.kafkaTemplate = kafkaTemplate;
        this.documentService = documentService;
    }

    @Override
    public LoanApplicationResponseDTO createLoanApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {

        String userID = loanApplicationRequestDTO.getCreatedBy();

        LoanApplication loanApplication = LoanApplication.builder()
                .loanType(LoanType.valueOf(loanApplicationRequestDTO.getLoanType()))
                .loanStage(LoanStage.valueOf(loanApplicationRequestDTO.getLoanStage()))
                .rcuStatus(RCUStatus.valueOf(loanApplicationRequestDTO.getRcuStatus()))
                .creditStatus(CreditStatus.valueOf(loanApplicationRequestDTO.getCreditStatus()))
                .createdBy(userID).build();

        System.out.println(loanApplication);
        loanApplicationRepository.save(loanApplication);

        ////Loan Stage history creation with KAFKA
        LoanStageHistoryRequestDTO loanStageHistoryRequestDTO = LoanStageHistoryRequestDTO.builder()
                .loanApplicationId(String.valueOf(loanApplication.getLoanID()))
                .changedBy(String.valueOf(userID))
                .oldStage(null)
                .currentStage(loanApplication.getLoanStage()).build();
        System.out.println(loanStageHistoryRequestDTO);
        //event making
        LoanStageHistoryEvent loanStageHistoryEvent = LoanStageHistoryEvent.builder().
                loanID(String.valueOf(loanApplication.getLoanID()))
                .userID(String.valueOf(userID))
                .loanStageHistoryRequestDTO(loanStageHistoryRequestDTO).build();

        System.out.println(loanStageHistoryEvent);
        System.out.println("Before Kafka Send");

        try {
            kafkaTemplate.send("loan-stage-history-events", loanStageHistoryEvent);

            System.out.println("MESSAGE SENT SUCCESSFULLY");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("After Kafka Send");
//        LoanStageHistoryResponseDTO loanStageHistoryResponseDTO =
//                createLoanStageHistory(String.valueOf(loanApplication.getLoanID()), String.valueOf(userID), loanStageHistoryRequestDTO);


        return LoanApplicationResponseDTO.builder()
                .loanID(loanApplication.getLoanID())
                .loanType(loanApplication.getLoanType())
                .loanStage(loanApplication.getLoanStage())
                .rcuStatus(loanApplication.getRcuStatus())
                .creditStatus(loanApplication.getCreditStatus())
                .createdAt(loanApplication.getCreatedAt())
                .createdBy(userID)
                .updatedAt(loanApplication.getUpdatedAt()).build();
    }

    @Override
    public List<LoanApplicationResponseDTO> getAllLoanApplicationByUserID(String userId) {
//        System.out.println("we inside of get all loans by userID");
        List<LoanApplication> loansList =
                loanApplicationRepository.findByCreatedBy(userId);

        List<LoanApplicationResponseDTO> responseList = new ArrayList<>();

        for (LoanApplication loan : loansList) {
            responseList.add(LoanApplicationResponseDTO.builder()
                    .loanID(loan.getLoanID())
                    .loanType(loan.getLoanType())
                    .loanStage(loan.getLoanStage())
                    .rcuStatus(loan.getRcuStatus())
                    .creditStatus(loan.getCreditStatus())
                    .createdBy(loan.getCreatedBy())
                    .createdAt(loan.getCreatedAt())
                    .updatedAt(loan.getUpdatedAt())
                    .build());
        }
//        System.out.println(responseList);
        return responseList;
    }


    @Override
    public LoanApplicationResponseDTO getLoanApplicationById(String loanId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(UUID.fromString(loanId)).orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application Not found"));
//        System.out.println("the loan is found");
        return LoanApplicationResponseDTO.builder()
                .loanID(loanApplication.getLoanID())
                .loanType(loanApplication.getLoanType())
                .loanStage(loanApplication.getLoanStage())
                .rcuStatus(loanApplication.getRcuStatus())
                .creditStatus(loanApplication.getCreditStatus())
                .createdBy(loanApplication.getCreatedBy())
                .createdAt(loanApplication.getCreatedAt())
                .updatedAt(loanApplication.getUpdatedAt()).build();
    }

    @Override
    public LoanApplicationResponseDTO updateLoanApplication(String loanID, LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LoanApplication loanApplication = loanApplicationRepository.findById(UUID.fromString(loanID)).orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application Not Found"));
        if (loanApplicationRequestDTO.getLoanType() != null) {
            loanApplication.setLoanType(LoanType.valueOf(loanApplicationRequestDTO.getLoanType()));
        }

        if (loanApplicationRequestDTO.getRcuStatus() != null) {
            loanApplication.setRcuStatus(RCUStatus.valueOf(loanApplicationRequestDTO.getRcuStatus()));
        }

        if (loanApplicationRequestDTO.getCreditStatus() != null) {
            loanApplication.setCreditStatus(CreditStatus.valueOf(loanApplicationRequestDTO.getCreditStatus()));
        }

        if (loanApplicationRequestDTO.getCreatedBy() != null) {
            loanApplication.setCreatedBy(loanApplicationRequestDTO.getCreatedBy());
        }

        loanApplicationRepository.save(loanApplication);

        return LoanApplicationResponseDTO.builder()
                .loanID(loanApplication.getLoanID())
                .loanType(loanApplication.getLoanType())
                .loanStage(loanApplication.getLoanStage())
                .rcuStatus(loanApplication.getRcuStatus())
                .creditStatus(loanApplication.getCreditStatus())
                .createdBy(loanApplication.getCreatedBy())
                .createdAt(loanApplication.getCreatedAt())
                .updatedAt(loanApplication.getUpdatedAt())
                .build();
    }

    @Override
    public LoanApplicationResponseDTO updateLoanApplicationStage(String loanID, LoanStage loanStage) {
        LoanApplication loanApplication =
                loanApplicationRepository.findById(UUID.fromString(loanID))
                        .orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application Not Found"));

        String userID = loanApplication.getCreatedBy();

        LoanStage oldStage = loanApplication.getLoanStage();

        if (oldStage == LoanStage.CREDIT_EVALUATION &&
                loanStage == LoanStage.CREDIT_EVALUATION) {
            throw new LoanStageTransitionNotAllowedException("Already in CREDIT_EVALUATION stage");
        }

// update stage
        loanApplication.setLoanStage(loanStage);

// save FIRST
        loanApplicationRepository.save(loanApplication);

//// THEN publish events
        kafkaTemplate.send("loan-stage-events",
                new LoanStageChangedEvent(
                        loanApplication.getLoanID(),
                        loanStage.name()
                ));

        LoanStageHistoryRequestDTO request = LoanStageHistoryRequestDTO.builder()
                .loanApplicationId(String.valueOf(loanApplication.getLoanID()))
                .changedBy(String.valueOf(userID))
                .oldStage(oldStage)
                .currentStage(loanStage)
                .build();

        kafkaTemplate.send("loan-stage-history-events",
                new LoanStageHistoryEvent(
                        String.valueOf(loanApplication.getLoanID()),
                        String.valueOf(userID),
                        request
                ));

        return LoanApplicationResponseDTO.builder()
                .loanID(loanApplication.getLoanID())
                .loanType(loanApplication.getLoanType())
                .loanStage(loanApplication.getLoanStage())
                .rcuStatus(loanApplication.getRcuStatus())
                .creditStatus(loanApplication.getCreditStatus())
                .createdBy(loanApplication.getCreatedBy())
                .createdAt(loanApplication.getCreatedAt())
                .updatedAt(loanApplication.getUpdatedAt())
                .build();
    }

    @Transactional
    @Override
    public void deleteLoanApplication(String loanId) {
        LoanApplication loanApplication = loanApplicationRepository.findById(UUID.fromString(loanId)).orElseThrow(() -> new LoanApplicationNotFoundException("Loan Application Not found"));
        //while deleting the loan application its history, documents and applicant should be deleted
        deleteAllLoanStageHistoryByLoanId(loanId);
//        documentService.deleteAllDocumentsByLoanId(loanId);
//        applicantService.deleteAllApplicantByLoanId(String.valueOf(loanApplication.getLoanID()));
        loanApplicationRepository.deleteById(loanApplication.getLoanID());
    }

    @Override
    public boolean isLoanApplicationExists(String loanID) {
        return loanApplicationRepository.existsByLoanID(UUID.fromString(loanID));
    }

    //LOAN STAGE HISTORY
    @Override
    public LoanStageHistoryResponseDTO createLoanStageHistory(String loanApplicationID, String userID, LoanStageHistoryRequestDTO loanStageHistoryRequestDTO) {
        System.out.println("creating the loan history");
        LoanStageHistory loanStageHistory = new LoanStageHistory();

        if (isLoanApplicationExists(loanApplicationID)) {

            LoanApplication loanApplication = LoanApplication.builder().loanID(UUID.fromString(loanApplicationID)).build();
//            UUID userId = UUID.fromString(userID);

            loanStageHistory.setLoanApplication(loanApplication);
            loanStageHistory.setChangedBy(userID);
            loanStageHistory.setOldStage(loanStageHistoryRequestDTO.getOldStage());
            loanStageHistory.setCurrentStage(loanStageHistoryRequestDTO.getCurrentStage());

            loanStageHistoryRepository.save(loanStageHistory);

        } else {
            throw new LoanApplicationNotFoundException("Loan Application is not found.");
        }
        System.out.println(loanStageHistory);

        return LoanStageHistoryResponseDTO.builder()
                .loanStageHistoryId(loanStageHistory.getLoanStageHistoryId())
                .loanApplicationId(loanStageHistory.getLoanApplication().getLoanID())
                .oldStage(loanStageHistory.getOldStage())
                .currentStage(loanStageHistory.getCurrentStage())
                .changedAt(loanStageHistory.getChangedAt())
                .changedBy(loanStageHistory.getChangedBy()).build();

    }

    @Override
    public List<LoanStageHistoryResponseDTO> getAllLoanStageHistoryByLoanId(String loanId) {
        List<LoanStageHistory> loanStageHistories = loanStageHistoryRepository.findByLoanApplicationLoanID(UUID.fromString(loanId));
        List<LoanStageHistoryResponseDTO> loanStageHistorylist = new ArrayList<>();

        for (int i = 0; i < loanStageHistories.size(); i++) {
            LoanStageHistoryResponseDTO singleLoanStageHistoryResponseDTO = LoanStageHistoryResponseDTO.builder()
                    .loanStageHistoryId(loanStageHistories.get(i).getLoanStageHistoryId())
                    .loanApplicationId(loanStageHistories.get(i).getLoanApplication().getLoanID())
                    .oldStage(loanStageHistories.get(i).getOldStage())
                    .currentStage(loanStageHistories.get(i).getCurrentStage())
                    .changedAt(loanStageHistories.get(i).getChangedAt())
                    .changedBy(loanStageHistories.get(i).getChangedBy()).build();
            loanStageHistorylist.add(singleLoanStageHistoryResponseDTO);
        }

        return loanStageHistorylist;
    }

    @Override
    public LoanStageHistoryResponseDTO getLoanStageHistoryById(String loanStageHistoryId) {

        LoanStageHistory loanstageHistory = loanStageHistoryRepository.findById(UUID.fromString(loanStageHistoryId)).orElseThrow(() -> new LoanStageHistoryNotFoundException("Loan Stage History Not Found."));

        return LoanStageHistoryResponseDTO.builder()
                .loanStageHistoryId(loanstageHistory.getLoanStageHistoryId())
                .loanApplicationId(loanstageHistory.getLoanApplication().getLoanID())
                .oldStage(loanstageHistory.getOldStage())
                .currentStage(loanstageHistory.getCurrentStage())
                .changedAt(loanstageHistory.getChangedAt())
                .changedBy(loanstageHistory.getChangedBy()).build();
    }

    @Override
    public void deleteLoanStageHistoryById(String loanStageHistoryId) {
        LoanStageHistory loanstageHistory = loanStageHistoryRepository.findById(UUID.fromString(loanStageHistoryId)).orElseThrow(() -> new LoanStageHistoryNotFoundException("Loan Stage History Not Found."));
        loanStageHistoryRepository.delete(loanstageHistory);
    }

    @Override
    @Modifying
    @Transactional
    public void deleteAllLoanStageHistoryByLoanId(String LoanId) {
        Long count = loanStageHistoryRepository.deleteAllByLoanApplicationLoanID(UUID.fromString(LoanId));
        //commenting this because if the histories are not present we must show no exception to stop the flow
        //
//        if (count == 0) {
//            throw new LoanStageHistoryNotFoundException("No history found to delete");
//        }
    }

    @Override
    public LoanStageHistoryResponseDTO updateLoanStageHistory(
            String loanStageHistoryId,
            LoanStageHistoryRequestDTO loanStageHistoryRequestDTO) {

        LoanStageHistory loanstageHistory =
                loanStageHistoryRepository.findById(UUID.fromString(loanStageHistoryId))
                        .orElseThrow(() ->
                                new LoanStageHistoryNotFoundException("Loan Stage History Not Found."));

        if (loanStageHistoryRequestDTO.getLoanApplicationId() != null) {

            if (!isLoanApplicationExists(
                    loanStageHistoryRequestDTO.getLoanApplicationId())) {

                throw new LoanApplicationNotFoundException(
                        "Loan Application Not Found");
            }

            LoanApplication loanApplication =
                    LoanApplication.builder()
                            .loanID(UUID.fromString(
                                    loanStageHistoryRequestDTO.getLoanApplicationId()))
                            .build();

            loanstageHistory.setLoanApplication(loanApplication);
        }

        if (loanStageHistoryRequestDTO.getCurrentStage() != null) {
            loanstageHistory.setCurrentStage(
                    loanStageHistoryRequestDTO.getCurrentStage());
        }

        if (loanStageHistoryRequestDTO.getOldStage() != null) {
            loanstageHistory.setOldStage(
                    loanStageHistoryRequestDTO.getOldStage());
        }

        if (loanStageHistoryRequestDTO.getChangedBy() != null) {
            loanstageHistory.setChangedBy(
                    loanStageHistoryRequestDTO.getChangedBy());
        }

        loanStageHistoryRepository.save(loanstageHistory);

        return LoanStageHistoryResponseDTO.builder()
                .loanStageHistoryId(loanstageHistory.getLoanStageHistoryId())
                .loanApplicationId(loanstageHistory.getLoanApplication().getLoanID())
                .oldStage(loanstageHistory.getOldStage())
                .currentStage(loanstageHistory.getCurrentStage())
                .changedAt(loanstageHistory.getChangedAt())
                .changedBy(loanstageHistory.getChangedBy())
                .build();
    }
}
