package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Category;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.example")
class CategoryServiceImplTest {

	@MockBean
	CategoryRepository dao;
	
	@Autowired
	CategoryServiceImpl srv;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void addTest() throws DuplicateKeyException, InvalidDataException {
		
		var category = new Category(20, "Fantasy");
		
		when(dao.save(category)).thenReturn(category);
		
		var rslt = srv.add(category);
		
		assertEquals(category, rslt);
	}
	@Test
	void modifyTest() throws InvalidDataException, NotFoundException {
		
		var category = new Category(20, "Fantasy");
		
		when(dao.existsById(20)).thenReturn(true);
		when(dao.save(category)).thenReturn(category);
		
		var rslt = srv.modify(category);
		verify(dao, times(1)).existsById(20);
		assertEquals(category, rslt);
	}
	@Test
	void deleteTest() throws InvalidDataException, NotFoundException {
		
		var category = new Category(1);
		
		srv.deleteById(category.getCategoryId());
		
		verify(dao, times(1)).deleteById(1);	
	}

}
