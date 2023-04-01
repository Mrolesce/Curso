package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class OK {
		@Test
		void validCategoryTest() {
			var category = new Category(18, "Categoria nueva");
			
			assertTrue(category.isValid());
		}
	}
	
	@Nested
	class KO {
		@ParameterizedTest
		@CsvSource(value= {
				"'','ERRORES: name: must not be blank'",
				"' ','ERRORES: name: must not be blank'",				
		})
		void invalidCategoryTest(String value, String error) {
			var category = new Category(18, value);
			
			assertTrue(category.isInvalid());
			assertEquals(error, category.getErrorsMessage());
		}
	}

}
