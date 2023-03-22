package com.example.ejemplos;

import java.math.BigDecimal;

public class Calculadora {
	public BigDecimal suma(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}

	public BigDecimal divide(BigDecimal a, BigDecimal b) {
		return a.divide(b);
	}
	
	public double divide(double a, double b) {
		return a / b;
	}
}