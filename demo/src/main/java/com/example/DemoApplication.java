package com.example;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.Rango;
import com.example.ioc.StringService;
import com.example.ioc.UnaTonteria;

import lombok.Data;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	@Qualifier("Remoto")
	private StringService srv;
	
	@Autowired
	@Qualifier("Local")
	private StringService srvLocal;
	
	@Value("${mi.valor:(Sin valor)}")
	private String configString;
	
	@Autowired
	Rango rango;
	
	@Autowired
	UnaTonteria tonteria;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
		
		System.out.println(srv.get(1));
		
		System.out.println(srvLocal.get(1));
		
		srv.add("Este es el remoto");
		srvLocal.add("Este es el local");
		
		System.out.println(configString);
		System.out.println(rango.toString());
		System.out.println(tonteria.dimeAlgo());
		
		System.out.println(tonteria != null ? tonteria.dimeAlgo() : "Tonteria nula");
	}

}
