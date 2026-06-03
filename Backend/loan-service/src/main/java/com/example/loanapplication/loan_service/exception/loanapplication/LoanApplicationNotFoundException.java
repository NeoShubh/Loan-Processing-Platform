package com.example.loanapplication.loan_service.exception.loanapplication;

public class LoanApplicationNotFoundException extends RuntimeException{
    public LoanApplicationNotFoundException(String msg){
        super(msg);
    }
}
