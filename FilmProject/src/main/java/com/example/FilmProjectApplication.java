package com.example;

import java.math.BigDecimal;

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
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.FilmCategoryPK;
import com.example.domains.entities.Language;

@SpringBootApplication
public class FilmProjectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FilmProjectApplication.class, args);
	}
	
//	@Autowired
//	ActorService srv;
//	
//	@Autowired
//	CategoryService srv2;
	
	@Autowired
	FilmService srv;
//	
//	@Autowired
//	LanguageService srv4;

	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("-----------------------> App iniciada");
		
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
		
//		srv4.getAll().forEach(System.out::println);
		
//		Actor a = new Actor(0, "Hola", "quedsadsa");
//
//		Category c = new Category(0, "Drama");

//		FilmCategory fc = new FilmCategory(new FilmCategoryPK(f.getFilmId(), (byte)c.getCategoryId()), c, f);
		
//		if(a.isInvalid())
//			System.err.println(a.getErrors());		
//		else
//			System.out.println("Pelicula valida");
//		
//		System.out.println(fc.toString());
		
		var peli = new Film("Hola mundo", new Language(2));
		peli.setRentalDuration((byte) 3);
		peli.setRating(Rating.ADULTS_ONLY);
		peli.setLength(10);
		peli.setReplacementCost(new BigDecimal(10.0));
		peli.setRentalRate(new BigDecimal(10.0));
		peli.setDescription("Trepidante aventura del nuevo mundo");
		peli.addActor(1);
		peli.addActor(2);
		peli.addActor(3);
		peli.addCategory(2);
		peli.addCategory(3);
		peli.addCategory(4);
		
		srv.add(peli);
	}

}
