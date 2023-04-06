package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

import jakarta.validation.Valid;
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
			return (List<FilmShortDTO>) srv.getByProjection(Sort.by(sort), FilmShortDTO.class);
		return srv.getByProjection(FilmShortDTO.class);
	}
	
//	@PostMapping
//	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
//		var newItem = srv.add(ActorDTO.from(item));
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//			.buildAndExpand(newItem.getActorId()).toUri();
//		return ResponseEntity.created(location).build();
//
//	}
	
	
}
