package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSumaPositivoPositivo() {
		var calc = new Calculadora();
		
		var a = new BigDecimal("2");
		var b = new BigDecimal("2");
		var r = new BigDecimal("4");
		
		var rslt = calc.suma(a, b);
		
		
		assertEquals(r, rslt);
	}
	
	@Test
	void testSumaPositivoPositivoEnteroDecimal() {
		var calc = new Calculadora();
		
		var a = new BigDecimal("2");
		var b = new BigDecimal("2.98");
		var r = new BigDecimal("4.98");
		
		var rslt = calc.suma(a, b);
		
		
		assertEquals(r, rslt);
	}

	@Test
	void testSumaPositivoNegativo() {
		var calc = new Calculadora();
		
		var a = new BigDecimal("1");
		var b = new BigDecimal("-0.9");
		var r = new BigDecimal("0.1");
		
		var rslt = calc.suma(a, b);
		
		assertEquals(r, rslt);
	}

	@Test
	void testSumaNegativoPositivo() {
		var calc = new Calculadora();
		
		var a = new BigDecimal("-1");
		var b = new BigDecimal("5");
		var r = new BigDecimal("4");
		
		var rslt = calc.suma(a, b);
		
		assertEquals(r, rslt);
	}

	@Test
	void testSumaNegativoNegativo() {
		var calc = new Calculadora();
		
		var a = new BigDecimal("-1");
		var b = new BigDecimal("5");
		var r = new BigDecimal("4");
		
		var rslt = calc.suma(a, b);
		
		assertEquals(r, rslt);
	}

	@Test
	void testSumaDecimale() {
		var calc = new Calculadora();
		
		var a = new BigDecimal("0.1");
		var b = new BigDecimal("0.2");
		var r = new BigDecimal("0.3");
		
		var rslt = calc.suma(a, b);
		
		assertEquals(r, rslt);
	}

	@Test
	void testDividir() {
		var calc = new Calculadora();
		
		var a = new BigDecimal("1");
		var b = new BigDecimal("2");
		var r = new BigDecimal("0.5");
		
		var rslt = calc.divide(a, b);
		
		assertEquals(r, rslt);
	}
	
	@Test
	void testDividirInfito() {
		var calc = new Calculadora();
		
		var rslt = calc.divide(1.0, 0);
		
		assertEquals(Double.POSITIVE_INFINITY, rslt);
	}
	
	@Test
	void testDividirInfinitoNegativo() {
		var calc = new Calculadora();
		
		var rslt = calc.divide(-1, 0);
		
		assertEquals(Double.NEGATIVE_INFINITY, rslt);
	}

}