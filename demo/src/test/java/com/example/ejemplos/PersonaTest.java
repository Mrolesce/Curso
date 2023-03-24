package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import com.example.core.test.Smoke;
import com.example.exceptions.InvalidDataException;
import com.example.ioc.PersonaRepository;

class PersonaTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Smoke
	@Test
	void test() {
		//constructor @Builder de lombok
		var p = Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build();
		
		//se ejecutan todos los tests
		
		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validación objeto",
				() -> assertEquals(1, p.getId(), "id"),
				() -> assertEquals("Pepito", p.getNombre(), "getNombre"),
				() -> assertEquals("Grillo", p.getApellidos().get(), "getApellidos"));
		
	}
	
	//se ejecutan todos los tests
	@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}")
	void repeatedTest(RepetitionInfo repetitionInfo) {
		var p = Persona.builder()
				.id(repetitionInfo.getCurrentRepetition())
				.nombre("Pepito" + (repetitionInfo.getCurrentRepetition() % 3 == 0 ? "" : repetitionInfo.getCurrentRepetition()))
				.apellidos("Grillo").build();
		
		assertNotNull(p);
		assertTrue(p instanceof Persona, "No es instancia de persona");
		assertAll("Validar propiedades",
			() -> assertEquals(repetitionInfo.getCurrentRepetition(), p.getId(), "id"),
			() -> assertEquals("Pepito" + repetitionInfo.getCurrentRepetition(), p.getNombre(), "getNombre"),
			() -> assertEquals("Grillo", p.getApellidos().get(), "getApellidos")
		);
	}
	
	@Nested
	class PersonaRepositoryTest{
		
		PersonaRepository dao;
		
		@BeforeEach
		void setUp() {
			dao = mock(PersonaRepository.class);
		}
		
		@Test
		void testLoad() {
			PersonaRepository dao = mock(PersonaRepository.class);
			when(dao.load()).thenReturn(Persona.builder().id(1).nombre("Pepito").apellidos("Grillo").build());
			
			var p = dao.load();
			
			assertNotNull(p);
			assertTrue(p instanceof Persona, "No es instancia de persona");
			assertAll("Validación objeto",
					() -> assertEquals(1, p.getId(), "id"),
					() -> assertEquals("Pepito", p.getNombre(), "getNombre"),
					() -> assertEquals("Grillo", p.getApellidos().get(), "getApellidos"));
		}
		
		@Test
		void testSave() throws InvalidDataException {
			PersonaRepository dao = mock(PersonaRepository.class);
			doThrow(new IllegalArgumentException()).when(dao).save(null);
			
			assertThrows(IllegalArgumentException.class, () -> dao.save(null));
		}
	}

}
