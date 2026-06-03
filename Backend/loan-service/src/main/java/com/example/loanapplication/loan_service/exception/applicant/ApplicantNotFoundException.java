package com.example.loanapplication.loan_service.exception.applicant;

public class ApplicantNotFoundException extends RuntimeException{
    public  ApplicantNotFoundException(String msg){
        super(msg);
    }
}
