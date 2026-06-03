package com.example.loanapplication.loan_service.modules.loanapplicationmodule.service;

//import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
//import com.example.loanapplication.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
//import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationRequestDTO;
//import com.example.loanapplication.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationResponseDTO;
//import com.example.loanapplication.modules.loanapplicationmodule.enums.LoanStage;

import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanStageHistoryDTO.LoanStageHistoryResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationRequestDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.dto.loanapplicationDTO.LoanApplicationResponseDTO;
import com.example.loanapplication.loan_service.modules.loanapplicationmodule.enums.LoanStage;

import java.util.List;
//import com.example.loanapplication.modules.loanapplicationmodule.entity.LoanApplication;

public interface LoanApplicationService {

    //Loan Application methods
    LoanApplicationResponseDTO createLoanApplication(LoanApplicationRequestDTO loanApplicationRequestDTO);

    List<LoanApplicationResponseDTO> getAllLoanApplicationByUserID(String userId);

    LoanApplicationResponseDTO getLoanApplicationById(String loanId);

    LoanApplicationResponseDTO updateLoanApplication(String loanID, LoanApplicationRequestDTO loanApplicationRequestDTO);

    LoanApplicationResponseDTO updateLoanApplicationStage(String loanID, LoanStage loanStage);

    void deleteLoanApplication(String loanId);

    boolean isLoanApplicationExists(String loanID);

    //Loan Application History methods
    LoanStageHistoryResponseDTO createLoanStageHistory(String loanApplicationID, String userID, LoanStageHistoryRequestDTO loanStageHistoryRequestDTO);

    List<LoanStageHistoryResponseDTO> getAllLoanStageHistoryByLoanId(String LoanId);

    LoanStageHistoryResponseDTO getLoanStageHistoryById(String loanStageHistoryId);

    void deleteLoanStageHistoryById(String loanStageHistoryId);

    void deleteAllLoanStageHistoryByLoanId(String LoanId);

    LoanStageHistoryResponseDTO updateLoanStageHistory(String loanStageHistoryId, LoanStageHistoryRequestDTO loanStageHistoryRequestDTO);

}
