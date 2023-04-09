package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import jakarta.transaction.Transactional;


@SpringBootApplication
public class FilmProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FilmProjectApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("------------------> Aplicacion iniciada");

		
	}

}