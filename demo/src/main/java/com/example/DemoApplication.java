package com.example;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;

import jakarta.transaction.Transactional;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		//var actor = new Actor(0, "Pepito", "Grillo");
		//dao.save(actor);
//		var item = dao.findById(202);
//		if (item.isPresent()) {
//			var actor = item.get();
//			actor.setFirstName("JOSEPH");
//			actor.setLastName("GRILLON'T");
//			dao.save(actor);
//		}else {
//			System.out.println("Actor no encontrado");
//		}
//		dao.findAll().forEach(System.out::println);
//		
//		dao.findTop5ByFirstNameStartingWithOrderByLastName("P")
//		.forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("P", Sort.by("FirstName")).forEach(System.out::println);
		
//		dao.findConJPQL(5).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 5))
//		.forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.greaterThan(root.get("actorId"), 200))
//		.forEach(System.out::println);
//		var item = dao.findById(1);
//		if (item.isPresent()) {
//			var actor = item.get();
//			System.out.println(actor);
//			
//			actor.getFilmActors().forEach(o -> System.out.println(o.getFilm().getTitle()));
//			
//		}else {
//			System.out.println("Actor no encontrado");
//		}
		var actor = new Actor(0, "Marc", "ROLES");
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		var err = validator.validate(actor);
		if(err.size()>0) {
			err.forEach(e -> System.out.println(e.getPropertyPath() + ": " + e.getMessage()));
		}else {
			dao.save(actor);
		}
		if (actor.isInvalid()) {
			System.out.println(actor.getErrorsMessage());
		}else {
			dao.save(actor);
			System.out.println(actor.getFirstName() + " " + actor.getLastName());
		}
		
	}

}
