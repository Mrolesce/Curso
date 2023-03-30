package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;

@SpringBootApplication
public class FilmProjectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FilmProjectApplication.class, args);
	}
	
	@Autowired
	ActorService srv;
	
	@Autowired
	CategoryService srv2;
	
	@Autowired
	FilmService srv3;
	
	@Autowired
	LanguageService srv4;
	
	@Override
	public void run(String... args) throws Exception {
		
//		var actor = new Actor(0, "Marc", "Roles");
//		
//		if(actor.isInvalid())
//			System.err.println(actor.getErrorsMessage());
//		else
//			srv.add(actor);
//		
//		
//		srv.getAll().stream().forEach(System.out::println);
//		
//		var category = new Category(0, "Terror");
//		
//		if(category.isInvalid())
//			System.err.println(category.getErrorsMessage());
//		else
//			srv2.add(category);
//		
//		srv2.getAll().forEach(System.out::println);
		
//		srv3.getAll().forEach(System.out::println);
		
		srv4.getAll().forEach(System.out::println);
		
	}

}
