package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class FilmServiceImplTest {

	@MockBean
	FilmRepository dao;
	
	@Autowired
	FilmServiceImpl srv;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
		
		when(dao.save(film)).thenReturn(film);
		
		var rslt = srv.add(film);
		
		assertEquals(film, rslt);
	}

	@Test
	void testModify() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
