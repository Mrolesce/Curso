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

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmCategory;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CategoryService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	class OK {
		@Test
		void testGetOne() throws Exception {
			int id = 1;
			var ele = new Category(id, "Action");
			when(srv.getOne(id)).thenReturn(Optional.of(ele));
			mockMvc.perform(get("/api/categorias/v1/{id}", id))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(id))
		        .andExpect(jsonPath("$.categoria").value(ele.getName()))
		        .andDo(print());
		}
		
		@Test
		void testGetAllString() throws Exception {
			List<Category> lista = new ArrayList<>(
			        Arrays.asList(new Category(1, "Action"),
			        		new Category(2, "Horror"),
			        		new Category(3, "Comedy")));
			when(srv.getByProjection(Category.class)).thenReturn(lista);
			mockMvc.perform(get("/api/categorias/v1").accept(MediaType.APPLICATION_JSON))
				.andExpectAll(
						status().isOk(), 
						content().contentType("application/json"),
						jsonPath("$.size()").value(3))
						.andDo(print());
		}
		
		@Test
		void testGetAllPageable() throws Exception {
			List<Category> lista = new ArrayList<>(
			        Arrays.asList(new Category(1, "Action"),
			        		new Category(2, "Horror"),
			        		new Category(3, "Comedy")));
			when(srv.getByProjection(PageRequest.of(0, 20), Category.class))
				.thenReturn(new PageImpl<>(lista));
			mockMvc.perform(get("/api/categorias/v1").queryParam("page", "0"))
				.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.content.size()").value(3),
					jsonPath("$.size").value(3)
					).andDo(print());
		}


		@Test
		void getCategoryPelisTest() throws Exception{
			int id = 1;
			
			var category = new Category(1, "Action");
			
			var film = new Film("Hola mundo", new Language(2), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var film2 = new Film("Adios mundo", new Language(1), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));
			var film3 = new Film("Bienvenido mundo", new Language(1), (byte)1, new BigDecimal(10.0), 1, new BigDecimal(10.0));

			List<FilmCategory> lista = new ArrayList<>(
			        Arrays.asList(new FilmCategory(film, category),
			        		new FilmCategory(film2, category),
			        		new FilmCategory(film3, category)));
			
			category.setFilmCategories(lista);
			
			when(srv.getOne(id)).thenReturn(Optional.of(category));
			mockMvc.perform(get("/api/categorias/v1/{id}/pelis", id))
	        	.andExpect(status().isOk())
	        	.andExpect(jsonPath("$[0].value").value("Hola mundo"))
	        	.andExpect(jsonPath("$[1].value").value("Adios mundo"))
	        	.andExpect(jsonPath("$[2].value").value("Bienvenido mundo"))
	        	.andDo(print());
		}
		
		@Test
		void testCreate() throws Exception {
			int id = 1;
			var ele = new Category(id, "Action");
			when(srv.add(ele)).thenReturn(ele);
			mockMvc.perform(post("/api/categorias/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ele))
				)
				.andExpect(status().isCreated())
		        .andExpect(header().string("Location", "http://localhost/api/categorias/v1/1"))
		        .andDo(print())
		        ;
		}

		@Test
		void testUpdate() throws Exception {
			var category = new Category(20, "Fantasy");
			
			category.setName("Drama");
			
			when(srv.modify(category)).thenReturn(category);
			
			mockMvc.perform(put("/api/categorias/v1/{id}", category.getCategoryId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(category))
					)
				.andExpect(status().isNoContent());
		}

		@Test
		void testDelete() throws Exception {
			mockMvc.perform(delete("/api/categorias/v1/{id}", 1))
	        		.andExpect(status().isNoContent());
			
			verify(srv, times(1)).deleteById(1);

		}
	}
	
	@Nested
	class KO {
		@Test
		void testGetOne404() throws Exception {
			int id = 1;
			var ele = new Category(id, "Action");
			when(srv.getOne(id)).thenReturn(Optional.empty());
			mockMvc.perform(get("/api/categorias/v1/{id}", id))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not found"))
		        .andDo(print());
		}
	}
	

}