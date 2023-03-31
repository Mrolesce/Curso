package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ActorTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class OK{
		@Test
		void testIsValid() {
			var actor = new Actor(0, "Pepito", "Grillo");
			assertTrue(actor.isValid());
		}
	}
	@Nested
	class KO{
		@ParameterizedTest
		@CsvSource(value= {
				"'','ERRORES: firstName: must not be blankfirstName: size must be between 2 and 45'",
				"' ','ERRORES: firstName: must not be blankfirstName: size must be between 2 and 45'",
				"'    ','ERRORES: firstName: must not be blank'",
				
				
		})
		void testIsValid(String valor, String error) {
			var actor = new Actor(0, valor, "Grillo");
			assertTrue(actor.isInvalid());
			assertEquals(error, actor.getErrorsMessage());
		}
	}
	
	

}
