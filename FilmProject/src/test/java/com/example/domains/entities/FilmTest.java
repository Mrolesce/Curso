package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

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
