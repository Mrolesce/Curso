package com.example.application.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.applications.services.CatalogoService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.NovedadesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CatalogoResource.class)
class CatalogoResourceTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CatalogoService srv;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetNovedades() throws Exception {
		Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
		
		var news = new NovedadesDTO();
		
		news.setActors(new ArrayList<ActorDTO>());
		news.setCategories(new ArrayList<Category>());
		news.setFilms(new ArrayList<FilmEditDTO>());
		news.setLanguages(new ArrayList<Language>());
		
		when(srv.novedades(fecha)).thenReturn(news);
		
		var result = mockMvc.perform(get("/api/catalogo/v1/novedades/v1").param("fecha", fecha.toString()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.films").isArray())
			.andExpect(jsonPath("$.actors").isArray())
			.andExpect(jsonPath("$.categories").isArray())
			.andExpect(jsonPath("$.languages").isArray())
		    .andDo(print()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}

}
