package com.fiap.foodcore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodcoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FoodcoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("###################################");
		System.out.println("LINK SWAGGER: http://localhost:8080/swagger-ui.html");
		System.out.println("###################################");
	}
}
