package com.example.loanapplication.rcu_service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RcuServiceApplication {
	@Value("${spring.data.mongodb.uuid-representation:NOT_FOUND}")
	private String uuidRepresentation;

	@PostConstruct
	public void init() {
		System.out.println("UUID REP = " + uuidRepresentation);
	}


	public static void main(String[] args) {


		SpringApplication.run(RcuServiceApplication.class, args);
	}

}
