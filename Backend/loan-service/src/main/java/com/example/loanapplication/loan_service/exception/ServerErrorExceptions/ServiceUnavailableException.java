package com.example.loanapplication.loan_service.exception.ServerErrorExceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
