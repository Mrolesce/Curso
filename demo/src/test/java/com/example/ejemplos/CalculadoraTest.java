package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CalculadoraTest {
	
	Calculadora calc;
	
	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}
	
	@Nested
	@DisplayName("Pruebas del método suma")
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class suma{
		@Nested
		class OK{
			
			@Test
			void test_Suma_Positivo_Positivo() {
				var calc = new Calculadora();
				
				var rslt = calc.suma(2, 2);
				
				
				assertEquals(4, rslt);
			}
			
			@Test
			void testSumaPositivoPositivoEnteroDecimal() {
				var calc = new Calculadora();
				
				var rslt = calc.suma(2, 2.98);
				
				
				assertEquals(4.98, rslt);
			}

			@Test
			void testSumaPositivoNegativo() {
				var calc = new Calculadora();
				
				var rslt = calc.suma(1, -0.9);
				
				assertEquals(0.1, rslt);
			}

			@Test
			void testSumaNegativoPositivo() {
				var calc = new Calculadora();
				
				var rslt = calc.suma(-1, 5);
				
				assertEquals(4, rslt);
			}

			@Test
			void testSumaNegativoNegativo() {
				var calc = new Calculadora();
				
				var rslt = calc.suma(-1, 5);
				
				assertEquals(4, rslt);
			}

			@Test
			void testSumaDecimale() {
				var calc = new Calculadora();
				
				var rslt = calc.suma(0.1, 0.2);
				
				assertEquals(0.3, rslt);
			}

		}
		@Nested
		class KO{
			
		}
	}
	
	@Nested
	@DisplayName("Pruebas del método divide")
	class divide {
		@Nested
		class OK {
			@Test
			void testDividir() {
				var calc = new Calculadora();
				
				var rslt = calc.divide(1, 2.0);
				
				assertEquals(0.5, rslt);
			}
			
			
		}
		@Nested
		class KO {
			@Test
			void testDividirInfito() {
				var calc = new Calculadora();
				
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
			}
			@Test
			void testDividirInfito2() {
				var calc = new Calculadora();
				
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0.0));
			}
			
			@Test
			void testDividirInfinitoNegativo() {
				var calc = new Calculadora();
				
				assertThrows(ArithmeticException.class, () -> calc.divide(-1, 0.0));
			}
		}
	}
	
	

}