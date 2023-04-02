package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FilmTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Nested
	class OK {
		@Test
		void validFilmTest() {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			
			assertTrue(film.isValid());
		}
		@Test
		void addActorTest() {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var actor1 = new Actor(0, "Marc", "Roles");
			
			film.addActor(actor1);
			
			assertTrue(film.getActors().contains(actor1));
		}
		@Test
		void getActorsTest() {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var actor1 = new Actor(0, "Marc", "Roles");
			var actor2 = new Actor(1, "Jose", "Antonio");
			
			film.addActor(actor1);
			film.addActor(actor2);
			
			List<Actor> actors = new ArrayList<Actor>();
			actors.add(actor1);
			actors.add(actor2);
			
			assertEquals(actors, film.getActors());
		}
		@Test
		void removeActorsTest() {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var actor1 = new Actor(0, "Marc", "Roles");
			var actor2 = new Actor(1, "Jose", "Antonio");
			
			film.addActor(actor1);
			film.addActor(actor2);
	
			film.removeActor(actor1);
			film.removeActor(actor2);
			
			assertTrue(film.getActors().isEmpty());
		}
		@Test
		void addCategoryTest() {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			
			var category = new Category(2);
			film.addCategory(category);
			
			assertTrue(film.getCategories().contains(category));
		}
		@Test
		void removeCategoriesTest() {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			
			var category = new Category(2);
			var category2 = new Category(3);
			film.addCategory(category);
			film.addCategory(category2);
			
			film.removeCategory(category);
			film.removeCategory(category2);
			
			assertFalse(film.getCategories().contains(category)
					&& film.getCategories().contains(category2));
		}
		@Test
		void mergeTest() {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var actor1 = new Actor(0, "Marc", "Roles");
			var actor2 = new Actor(1, "Jose", "Antonio");
			
			var category = new Category(2);
			var category2 = new Category(3);
			film.addActor(actor1);
			film.addActor(actor2);
			film.addCategory(category);
			film.addCategory(category2);
			
			var film2 = new Film("Adios mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var actor3 = new Actor(0, "Marc", "Roles");
			
			var category3 = new Category(4);
			film.addActor(actor3);
			film.addCategory(category3);
			
			film.merge(film2);
			
			assertAll("Multiple tests", 
					() -> assertTrue(film.getActors().contains(actor1)),
					() -> assertTrue(film.getActors().contains(actor3)),
					() -> assertTrue(film.getCategories().contains(category3)));
		}
	}
	
	@Nested
	class KO {
		@ParameterizedTest
		@CsvSource(value= {
				"'','ERRORES: title: must not be blank'",
				"' ','ERRORES: title: must not be blank'",
				"'fpiwfwfpiwnfwpfndwfpdkwnfkdwnfpiwnfpwnficdlkcdnscnwlfpiwfwfpiwnfwpfndwfpdkwnfkdwnfpiwnfpwnficdlkcdnscnwlfpiwfwfpiwnfwpfndwfpdkwnfkdwnfpiwnfpwnficdlkcdnscnwlfpiwfwfpiwnfwpfndwfpdkwnfkdwnfpiwnfpwnficdlkcdnscnwl','ERRORES: title: size must be between 0 and 128'"
		})
		void invalidTitleFilmTest(String value, String error) {
			var film = new Film(value, new Language(3), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			
			assertTrue(film.isInvalid());
			assertEquals(error, film.getErrorsMessage());
		}
		@Test
		void invalidLanguageFilmTest() {
			var film = new Film("Hello world", null, (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			
			assertTrue(film.isInvalid());
			assertEquals("ERRORES: language: must not be null", film.getErrorsMessage());
		}
		@Test
		void invalidRentalDurationFilmTest() {
			var film = new Film("Hello world", new Language(2), (byte)-1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			
			assertTrue(film.isInvalid());
			assertEquals("ERRORES: rentalDuration: must be greater than 0", film.getErrorsMessage());
		}
		@ParameterizedTest
		@CsvSource(value= {
				"'222.01','ERRORES: rentalRate: numeric value out of bounds (<2 digits>.<2 digits> expected)'",
				"'0','ERRORES: rentalRate: must be greater than 0rentalRate: must be greater than 0.0'",
		})
		void invalidRentalRateFilmTest(String value, String error) {
			var film = new Film("Hello world", new Language(2), (byte)1, new BigDecimal(value), 1, new BigDecimal(10.0));
			
			assertTrue(film.isInvalid());
			assertEquals(error, film.getErrorsMessage());
		}
		@ParameterizedTest
		@CsvSource(value= {
				"'2222.01','ERRORES: replacementCost: numeric value out of bounds (<3 digits>.<2 digits> expected)'",
				"'0',ERRORES: replacementCost: must be greater than 0.0",
		})
		void invalidReplacementCostFilmTest(String value, String error) {
			var film = new Film("Hello world", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(value));
			
			assertTrue(film.isInvalid());
			assertEquals(error, film.getErrorsMessage());
		}
	}

}
