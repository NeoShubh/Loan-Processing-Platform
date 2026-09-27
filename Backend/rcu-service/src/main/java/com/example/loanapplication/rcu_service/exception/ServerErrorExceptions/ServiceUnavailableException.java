package com.example.loanapplication.rcu_service.exception.ServerErrorExceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
