package com.project.lendmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LendmateApplication {
	public static void main(String[] args) {
		SpringApplication.run(LendmateApplication.class, args);
	}
}