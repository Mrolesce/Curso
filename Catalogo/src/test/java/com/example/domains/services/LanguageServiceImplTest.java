package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Language;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class LanguageServiceImplTest {

	@MockBean
	LanguageRepository dao;
	
	@Autowired
	LanguageServiceImpl srv;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void addTest() throws DuplicateKeyException, InvalidDataException {
		var lang = new Language(0, "Chinese");
		
		when(dao.save(lang)).thenReturn(lang);
		
		var rslt = srv.add(lang);
		
		assertEquals(lang, rslt);
	}
	@Test
	void modifyTest() throws InvalidDataException, NotFoundException {
		var lang = new Language(0, "Chinese");
		
		when(dao.existsById(lang.getLanguageId())).thenReturn(true);
		when(dao.save(lang)).thenReturn(lang);

		var rslt = srv.modify(lang);
		
		assertEquals(lang, rslt);
	}
	@Test
	void deleteTest() throws InvalidDataException {
		var lang = new Language(0, "Chinese");
		
		srv.delete(lang);
		
		verify(dao).deleteById(lang.getLanguageId());
	}
}
