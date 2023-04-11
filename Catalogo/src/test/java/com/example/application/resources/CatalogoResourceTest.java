package com.example.application.resources;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.applications.services.CatalogoService;
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
	void test() {
		fail("Not yet implemented");
	}

}
