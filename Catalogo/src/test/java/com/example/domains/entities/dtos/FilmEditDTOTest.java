package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;

class FilmEditDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void filmEditDTOToFilmTest() {
		var filmEditDTO = new FilmEditDTO();
		
		var film = FilmEditDTO.from(filmEditDTO);
		
		assertEquals(Film.class, film.getClass());
	}
	
	@Test
	void filmToFilmEditDTOTest() {
		var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
		
		var filmEditDTO = FilmEditDTO.from(film);
		
		assertEquals(FilmEditDTO.class, filmEditDTO.getClass());
	}

}
