package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.ioc.Rango;
import com.example.ioc.StringService;
import com.example.ioc.UnaTonteria;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;

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
		var item = dao.findById(1);
		if (item.isPresent()) {
			var actor = item.get();
			System.out.println(actor);
			
			actor.getFilmActors().forEach(o -> System.out.println(o.getFilm().getTitle()));
			
		}else {
			System.out.println("Actor no encontrado");
		}
		
	}

}
