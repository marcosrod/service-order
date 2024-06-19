package com.marcosrod.serviceorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class ServiceOrderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceOrderApiApplication.class, args);
	}

}
