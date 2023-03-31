package com.example.domains.contracts.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.domains.services.ActorServiceImpl;

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
	void test() {
		List<Actor> lista = new ArrayList<>(
				Arrays.asList(
						new Actor(1, "Pepito", "GRILLO"),
						new Actor(2, "Carmelo", "COTON"),
						new Actor(3, "Capitan", "TAN")));
		
		when(dao.findAll()).thenReturn(lista);
		
		assertEquals(lista, dao.findAll());
	}

}
