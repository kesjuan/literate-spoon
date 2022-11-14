package com.yourNewBank.Banking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourNewBank.Banking.model.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);

	}

}
