package com.example.application.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.FilmShortDTO;

import jakarta.validation.Validator;

@RestController
@RequestMapping(path = {"/api/films/v1", "/api/films"})
public class FilmResource {

	@Autowired
	FilmService srv;
	
	@Autowired
	Validator validator;
	
	@GetMapping
	public List<FilmShortDTO> getAll(@RequestParam(required = false) String sort) {
		if(sort != null)
			return (List<FilmShortDTO>)srv.getByProjection(Sort.by(sort), FilmShortDTO.class);
		return srv.getByProjection(FilmShortDTO.class);
	}
}
