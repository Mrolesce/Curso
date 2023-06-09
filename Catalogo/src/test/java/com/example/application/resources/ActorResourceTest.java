package com.example.application.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
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

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Value
	static class ActorShortMock implements ActorShort {
		int actorId;
		String nombre;
	}
	
	@Nested
	class OK {
		@Test
		void testGetAllString() throws Exception {
			List<ActorShort> lista = new ArrayList<>(
			        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
			        		new ActorShortMock(2, "Carmelo Coton"),
			        		new ActorShortMock(3, "Capitan Tan")));
			when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
			mockMvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3)
						);
//			mvc.perform(get("/api/v1/actores").accept(MediaType.APPLICATION_XML))
//				.andExpectAll(
//						status().isOk(), 
//						content().contentType("application/json"),
//						jsonPath("$.size()").value(3)
//						);
		}

		@Test
		void testGetAllPageable() throws Exception {
			List<ActorDTO> lista = new ArrayList<>(
			        Arrays.asList(new ActorDTO(1, "Pepito", "Grillo"),
			        		new ActorDTO(2, "Carmelo", "Coton"),
			        		new ActorDTO(3, "Capitan", "Tan")));
			when(srv.getByProjection(PageRequest.of(0, 20), ActorDTO.class))
				.thenReturn(new PageImpl<>(lista));
			mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.content.size()").value(3),
					jsonPath("$.size").value(3)
					);
		}

		@Test
		void testGetOne() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			when(srv.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/actores/v1/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(id))
		        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
		        .andExpect(jsonPath("$.apellidos").value(ele.getLastName()))
		        .andDo(print());
		}
		
		@Test
		void getFilmActorsTest() throws Exception {
			var actor = new Actor(1, "Marc", "ROLES");
			
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var film2 = new Film("Adios mundo", new Language(1), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var film3 = new Film("Bienvenido mundo", new Language(1), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			
			List<FilmActor> lista = new ArrayList<>(
			        Arrays.asList(new FilmActor(film, actor),
			        		new FilmActor(film2, actor),
			        		new FilmActor(film3, actor)));
			
			actor.setFilmActors(lista);
			
			when(srv.getOne(1)).thenReturn(Optional.of(actor));
			
			mockMvc.perform(get("/api/actores/v1/{id}/pelis", 1))
			    	.andExpect(status().isOk())
			    	.andExpect(jsonPath("$[0].value").value("Hola mundo"))
			    	.andExpect(jsonPath("$[1].value").value("Adios mundo"))
			    	.andExpect(jsonPath("$[2].value").value("Bienvenido mundo"))
			    	.andDo(print());
			
		}

		@Test
		void testCreate() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			when(srv.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/actores/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/actores/v1/1"))
		        .andDo(print())
		        ;
		}

		@Test
		void testUpdate() throws Exception {
			var actor = new Actor(20, "Andoni", "POLACO");
			
			actor.setFirstName("Tommy");
			actor.setLastName("HAHAS");
			
			when(srv.modify(actor)).thenReturn(actor);
			
			mockMvc.perform(put("/api/actores/v1/{id}", actor.getActorId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(actor))
					)
					.andExpect(status().isNoContent());
		}
		
		@Test
		void testDelete() throws Exception {
			mockMvc.perform(delete("/api/actores/v1/{id}", 1))
	        		.andExpect(status().isNoContent());
			
			verify(srv).deleteById(1);
		}

	}
	
	@Nested
	class KO {
		@Test
		void testGetOne404() throws Exception {
			int id = 1;
			var ele = new Actor(id, "Pepito", "Grillo");
			when(srv.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/actores/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not found"))
		        .andDo(print());
		}
		
		@Test
		void noBodyTest() throws Exception {
			mockMvc.perform(post("/api/actores/v1"))
				.andExpect(status().isBadRequest())
		        .andDo(print());
		}
		
		@Test
		void invalidRouteTest() throws Exception {
			mockMvc.perform(get("/hola/que/tal"))
				.andExpect(status().isNotFound())
		        .andDo(print());
		}
	}
}