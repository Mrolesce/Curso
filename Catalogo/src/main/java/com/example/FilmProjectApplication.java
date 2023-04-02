package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.applications.services.CatalogoService;
import com.example.domains.contracts.services.FilmService;

import jakarta.transaction.Transactional;


@SpringBootApplication
public class FilmProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FilmProjectApplication.class, args);
	}

	@Autowired
	FilmService srv;
	
	@Autowired
	CatalogoService catSrv;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("------------------> Aplicacion iniciada");

		
	}

}