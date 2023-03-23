package com.example.ejemplos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {
	public BigDecimal suma(BigDecimal a, BigDecimal b) {
		return a.add(b);
	}
	public double suma(double a, double b) {
		BigDecimal rslt = BigDecimal.valueOf(a + b);
		return rslt.setScale(15, RoundingMode.HALF_DOWN).doubleValue();
	}

	
	public double divide(double a, double b) {
		if(b == 0) throw new ArithmeticException("Divide by 0");
		return a / b;
	}
	
	public int divide(int a, int b) {
		
		return a / b;
	}
}