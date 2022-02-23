package com.webservices.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.webservices.application.security.AppProperties;

@SpringBootApplication
public class WebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServicesApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
//	@Bean(name = "AppProperties")
//	public AppProperties getAppProperteis() {
//		return new AppProperties();
//	}
	
}
