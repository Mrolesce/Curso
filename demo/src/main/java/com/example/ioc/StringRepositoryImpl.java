package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Primary
//@Qualifier("old")
//solo un primario en caso de tener que elegir un repositorio
public class StringRepositoryImpl implements StringRepository {
	private String ultimo = "";
	@Override
	public String load() {
		return "Soy el StringRepositoryImpl";
	}

	@Override
	public void save(String item) {
		System.out.println("Anterior: " + ultimo);
		this.ultimo = item;
		System.out.println("Guardo el item: " + item);
	}

}
