package com.example.loanapplication.rcu_service.exception;


import com.example.loanapplication.rcu_service.exception.rcuCase.RCUCaseIsNotAssignedException;
import com.example.loanapplication.rcu_service.exception.rcuCase.RCUCaseNotPresentException;
import com.example.loanapplication.rcu_service.exception.rcuCase.RCUStatusCanNotBeChangedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ApiError apiError = new ApiError("Validation failed", errors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.authorization.AuthorizationDeniedException.class)
    public ResponseEntity<?> handleAccessDenied() {
        ApiError apiError = new ApiError("Access Denied: You are not allowed to perform this action");
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RCUCaseNotPresentException.class)
    public ResponseEntity<?> handleRCUCaseNotPresentException(RCUCaseNotPresentException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RCUCaseIsNotAssignedException.class)
    public ResponseEntity<?> handleRCUCaseIsNotAssignedException(RCUCaseIsNotAssignedException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RCUStatusCanNotBeChangedException.class)
    public ResponseEntity<?> RCUStatusCanNotBeChangedException(RCUStatusCanNotBeChangedException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {

        ApiError apiError = new ApiError("Internal server error");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }

}
