package com.example.loanapplication.rcu_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RcuServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcuServiceApplication.class, args);
	}

}
