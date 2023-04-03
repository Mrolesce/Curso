package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.domains.services.ActorServiceImpl;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class ActorServiceImplTest {
	
	@MockBean
	ActorRepository dao;
	
	@Autowired
	ActorServiceImpl srv;
	
	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	void getAllTest() {
		List<Actor> lista = new ArrayList<>(
				Arrays.asList(
						new Actor(1, "Pepito", "GRILLO"),
						new Actor(2, "Carmelo", "COTON"),
						new Actor(3, "Capitan", "TAN")));
		
		when(dao.findAll()).thenReturn(lista);
		
		var rslt = srv.getAll();
		
		assertEquals(lista, rslt);
	}
	@Test
	void addTest() throws DuplicateKeyException, InvalidDataException {
		
		var actor = new Actor(1, "Pepito", "GRILLO");
		
		when(dao.save(actor)).thenReturn(actor);
		
		var rslt = srv.add(actor);
		
		assertEquals(actor, rslt);
	}
	@Test
	void modifyTest() throws InvalidDataException, NotFoundException {
		
		var actor = new Actor(1, "Pepito", "GRILLO");
		
		when(dao.existsById(1)).thenReturn(true);
		when(dao.save(actor)).thenReturn(actor);
		
		var rslt = srv.modify(actor);
		verify(dao).existsById(1);
		assertEquals(actor, rslt);
	}
	@Test
	void deleteTest() throws InvalidDataException, NotFoundException {
		
		var actor = new Actor(0, "Pepito", "GRILLO");
		
		srv.deleteById(0);
		
		verify(dao).deleteById(0);
		
	}

}
