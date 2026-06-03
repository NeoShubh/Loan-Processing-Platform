package com.example.loanapplication.loan_service.exception.loanapplication;

public class LoanStageTransitionNotAllowedException extends  RuntimeException{
    public LoanStageTransitionNotAllowedException(String msg){
        super(msg);
    }
}
