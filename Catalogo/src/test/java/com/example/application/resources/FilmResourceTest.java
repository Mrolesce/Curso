package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FilmResource.class)
class FilmResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private FilmService srv;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class OK {
		@Test
		void testGetOne() throws Exception {
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			when(srv.getOne(0)).thenReturn(Optional.of(film));
			mockMvc.perform(get("/api/films/v1/{id}", 0))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.filmId").value(0))
		        .andExpect(jsonPath("$.title").value("Hola mundo"))
		        .andDo(print());
		}
		
		@Test
		void testGetAll() throws Exception {
			List<FilmShortDTO> lista = new ArrayList<>(
			        Arrays.asList(new FilmShortDTO(1, "Hola Mundo"),
			        		new FilmShortDTO(2, "Adios Mundo"),
			        		new FilmShortDTO(3, "Bienvenido Mundo")));
			when(srv.getByProjection(FilmShortDTO.class)).thenReturn(lista);
			mockMvc.perform(get("/api/films/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3))
						.andDo(print());
		}
		
		@Test
		void testGetAllPageable() throws Exception {
			List<FilmShortDTO> lista = new ArrayList<>(
			        Arrays.asList(new FilmShortDTO(1, "Hola mundo"),
			        		new FilmShortDTO(2, "Adios mundo"),
			        		new FilmShortDTO(3, "Bienvenido mundo")));
			when(srv.getByProjection(PageRequest.of(0, 20), FilmShortDTO.class))
				.thenReturn(new PageImpl<>(lista));
			mockMvc.perform(get("/api/films/v1").queryParam("page", "0"))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.content.size()").value(3),
					jsonPath("$.size").value(3),
					jsonPath("$.content[0].title").value("Hola mundo"),
					jsonPath("$.content[1].title").value("Adios mundo"),
					jsonPath("$.content[2].title").value("Bienvenido mundo")
					).andDo(print());
		}
		
		@Test
		void testgetDetails() throws Exception {
			List<Integer> ac = new ArrayList<Integer>(Arrays.asList(1,2,3));
			List<Integer> ca = new ArrayList<Integer>(Arrays.asList(1,2,3));
			
			var film = new FilmEditDTO(1, "Trepidante aventura", 6, "R", (short) 2012, (byte)1, new BigDecimal(10.0), new BigDecimal(10.0), "Hola Mundo", 1, 2, ac, ca);
			
			when(srv.getOne(1)).thenReturn(Optional.of(FilmEditDTO.from(film)));
			
			mockMvc.perform(get("/api/films/v1/{id}/details", 1))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.filmId").value(1),
				jsonPath("$.title").value("Hola Mundo"),
				jsonPath("$.length").value(6),
				jsonPath("$.rating").value("R"),
				jsonPath("$.releaseYear").value(2012),
				jsonPath("$.rentalDuration").value(1),
				jsonPath("$.rentalRate").value(10.0),
				jsonPath("$.replacementCost").value(10.0),
				jsonPath("$.actors.length()").value(3),
				jsonPath("$.categories.length()").value(3)
				).andDo(print());
		}
		
		@Test
		void testCreate() throws Exception {
			List<Integer> ac = new ArrayList<Integer>(Arrays.asList(1,2,3));
			List<Integer> ca = new ArrayList<Integer>(Arrays.asList(1,2,3));

			var film = new FilmEditDTO(1, "Trepidante aventura", 6, "R", (short) 2012, (byte)1, new BigDecimal(10.0), new BigDecimal(10.0), "Hola Mundo", 1, 2, ac, ca);
			
			when(srv.add(FilmEditDTO.from(film))).thenReturn(FilmEditDTO.from(film));
			mockMvc.perform(post("/api/films/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(film))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/films/v1/1"))
		        .andDo(print());
		}
		
		@Test
		void testUpdate() throws Exception {
			List<Integer> ac = new ArrayList<Integer>(Arrays.asList(1,2,3));
			List<Integer> ca = new ArrayList<Integer>(Arrays.asList(1,2,3));

			var film = new FilmEditDTO(1, "Trepidante aventura", 6, "R", (short) 2012, (byte)1, new BigDecimal(10.0), new BigDecimal(10.0), "Hola Mundo", 1, 2, ac, ca);
			
			film.setTitle("Drama");
			
			when(srv.modify(FilmEditDTO.from(film))).thenReturn(FilmEditDTO.from(film));
			
			mockMvc.perform(put("/api/films/v1/{id}", film.getFilmId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(film))
					)
				.andExpect(status().isNoContent());
		}
		
	}
	
	@Nested
	class KO {
		@Test
		void testGetOne404() throws Exception {
			int id = 1;
			
			List<Integer> ac = new ArrayList<Integer>(Arrays.asList(1,2,3));
			List<Integer> ca = new ArrayList<Integer>(Arrays.asList(1,2,3));

			var film = new FilmEditDTO(1, "Trepidante aventura", 6, "R", (short) 2012, (byte)1, new BigDecimal(10.0), new BigDecimal(10.0), "Hola Mundo", 1, 2, ac, ca);
			
			when(srv.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/films/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not found"))
		        .andDo(print());
		}
	}

}
