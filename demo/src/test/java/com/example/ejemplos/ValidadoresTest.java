package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

class ValidadoresTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	
	@Nested
	class OK {
		@ParameterizedTest(name = "{0}")
		@CsvSource(value = {"12345678Z",
				"72849506L",
				"45673254S",
				"72298413Y",
				"37613561M",
				"89040872Y",
				"00000000T"})
		@NullSource
		void testNIFValidos(String dni) {
			
			var validador = new Validadores();
			
			assertTrue(validador.validarNif(dni));
		}
		
	}
	
	@Nested
	class KO {
		@Test
		void testNIFSinLetra() {
			String dni = "12345678";
			
			var validador = new Validadores();
			
			assertFalse(validador.validarNif(dni));
		}
		@Test
		void testNIFFaltaDeNumeros() {
			String dni = "123456Z";
			
			var validador = new Validadores();
			
			assertFalse(validador.validarNif(dni));
		}
		@Test
		void testNIFNumerosLetras() {
			String dni = "A34DSFS3P";
			
			var validador = new Validadores();
			
			assertFalse(validador.validarNif(dni));
		}
		@Test
		void testNIFLetrasDeMas() {
			String dni = "12345678ZZ";
			
			var validador = new Validadores();
			
			assertFalse(validador.validarNif(dni));
		}
		@Test
		void testNIFLetraInvalida() {
			String dni = "12345678A";
			
			var validador = new Validadores();
			
			assertFalse(validador.validarNif(dni));
		}
		@Test
		void noEsNifTest() {
			String dni = "12345678A";
			
			var validador = new Validadores();
			
			assertTrue(validador.noEsNif(dni));
		}
	}
	

}
