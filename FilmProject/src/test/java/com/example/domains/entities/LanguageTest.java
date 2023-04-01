package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Nested;

class LanguageTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class OK {
		@Test
		void validLanguageTest() {
			var language = new Language(0, "Esperanto");
			
			assertTrue(language.isValid());
		}
	}
	
	@Nested
	class KO {
		@ParameterizedTest
		@CsvSource(value= {
				"'','ERRORES: name: must not be blank'",
				"' ','ERRORES: name: must not be blank'",				
		})
		void validLanguageTest(String value, String errors) {
			var language = new Language(0, value);
			
			assertTrue(language.isInvalid());
			assertEquals(errors, language.getErrorsMessage());
		}
	}
	

}
