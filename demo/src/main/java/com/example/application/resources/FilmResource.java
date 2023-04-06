package com.example.application.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.FilmService;

import jakarta.validation.Validator;

@RestController
@RequestMapping(path = {"/api/films/v1", "/api/films"})
public class FilmResource {

	@Autowired
	FilmService srv;
	
	@Autowired
	Validator validator;
	
	

}
