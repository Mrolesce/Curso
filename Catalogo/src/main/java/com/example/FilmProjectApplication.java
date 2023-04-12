package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.transaction.Transactional;
@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio: Catalogo de peliculas",
                version = "1.0",
                description = "Ejemplo de Microservicio utilizando la base de datos **Sakila**.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Marc Roles", url = "https://github.com/Mrolesce", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/Mrolesce/Curso")
)
@SpringBootApplication
public class FilmProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FilmProjectApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("------------------> Aplicacion iniciada");

		
	}
	
//	@Bean
//	public OpenApiCustomiser sortSchemasAlphabetically() {
//		return openApi -> {
//			var schemas = openApi.getComponents().getSchemas();
//			openApi.getComponents().setSchemas(new TreeMap<>(schemas));
//		};
//	}

}