package com.example.loanapplication.rcu_service.exception.rcuCase;

public class RCUStatusCanNotBeChangedException extends RuntimeException {
    public RCUStatusCanNotBeChangedException(String message) {
        super(message);
    }
}
