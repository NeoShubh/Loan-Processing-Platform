package com.example.loanapplication.loan_service.external.services;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("AUTH-USER-SERVICE")
public interface AuthUserService {


}
