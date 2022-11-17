package com.example.banksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.naming.CommunicationException;
import java.net.ConnectException;

@SpringBootApplication
public class BankSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(BankSystemApplication.class, args);

	}

}
