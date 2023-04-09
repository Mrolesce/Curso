package com.example.application.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LanguageResource.class)
class LanguageResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private LanguageService srv;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class OK{
		@Test
		void testGetOne() throws Exception {
			int id = 1;
			var ele = new Language(id, "Action");
			when(srv.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/lenguajes/v1/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(id))
		        .andExpect(jsonPath("$.idioma").value(ele.getName()))
		        .andDo(print());
		}
		
		@Test
		void testGetAllString() throws Exception {
			List<Language> lista = new ArrayList<>(
			        Arrays.asList(new Language(1, "Polaco"),
			        		new Language(2, "Castellano"),
			        		new Language(3, "Rumano")));
			when(srv.getByProjection(Language.class)).thenReturn(lista);
			mockMvc.perform(get("/api/lenguajes/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3))
						.andDo(print());
		}
		
		@Test
		void getLanguageFilmsTest() throws Exception {
			int id = 1;
			
			var language = new Language(1, "Suajili");
			
			List<Film> films = new ArrayList<>(
			        Arrays.asList(new Film("Hola mundo", new Language(1), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0)),
			        		new Film("Adios mundo", new Language(1), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0)),
			        		new Film("Bienvenido mundo", new Language(1), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0))));
			
			language.setFilms(films);
			
			when(srv.getOne(id)).thenReturn(Optional.of(language));
			mockMvc.perform(get("/api/lenguajes/v1/{id}/pelis", id).accept(MediaType.APPLICATION_JSON))
					.andExpectAll(
							status().isOk(), 
							content().contentType("application/json"),
							jsonPath("$.size()").value(3)).andDo(print());

		}
		
		@Test
		void testCreate() throws Exception {
			int id = 1;
			var ele = new Language(id, "Suajili");
			when(srv.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/lenguajes/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ele))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/lenguajes/v1/1"))
		        .andDo(print())
		        ;
		}
		
		@Test
		void testUpdate() throws Exception {
			var language = new Language(1, "French");
			
			language.setName("Japanese");
			
			when(srv.modify(language)).thenReturn(language);
			
			mockMvc.perform(put("/api/lenguajes/v1/{id}", language.getLanguageId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(language))
					)
				.andExpect(status().isNoContent());
		}

		@Test
		void testDelete() throws Exception {
			mockMvc.perform(delete("/api/lenguajes/v1/{id}", 1))
	        		.andExpect(status().isNoContent());
		}
	}
	
	@Nested
	class KO {
		@Test
		void testGetOne404() throws Exception {
			int id = 1;
			var ele = new Language(id, "Suajili");
			when(srv.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/lenguajes/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not found"))
		        .andDo(print());
		}
	}

}
