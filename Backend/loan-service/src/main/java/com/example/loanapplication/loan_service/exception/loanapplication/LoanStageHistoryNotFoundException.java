package com.example.loanapplication.loan_service.exception.loanapplication;

public class LoanStageHistoryNotFoundException extends RuntimeException{
    public LoanStageHistoryNotFoundException(String msg){
        super(msg);
    }
}
