package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;

@SpringBootApplication
public class FilmProjectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FilmProjectApplication.class, args);
	}
	
	@Autowired
	ActorService srv;
	
	@Override
	public void run(String... args) throws Exception {
		
		var actor = new Actor(0, "Marc", "Roles");
		
		if(actor.isInvalid())
			System.err.println(actor.getErrorsMessage());
		else
			srv.add(actor);
		
		
		srv.getAll().stream().forEach(System.out::println);
	}

}
