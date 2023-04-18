package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmDetailsDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = {"/api/films/v1", "/api/films"})
public class FilmResource {

	@Autowired
	FilmService srv;
	
	@GetMapping
	public List<FilmShortDTO> getAll(@RequestParam(required = false) String sort) {
		if(sort != null)
			return (List<FilmShortDTO>) srv.getByProjection(Sort.by(sort), FilmShortDTO.class);
		return srv.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAll(Pageable pageable) {
		return srv.getByProjection(pageable, FilmShortDTO.class);
	}
	
	@GetMapping(params = "mode=details")
	public List<FilmDetailsDTO> getAllDetails(
//			@Parameter(allowEmptyValue = true, required = false, schema = @Schema(type = "string", allowableValues = {"details","short"}, defaultValue = "short")) 
			@RequestParam(defaultValue = "short") String mode) {
		return srv.getAll().stream().map(item -> FilmDetailsDTO.from(item)).toList();
	}
	
	@GetMapping(path = "/{id:\\d+}")//expresión genérica
	public FilmDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return FilmDTO.from(item.get());
	}
	
	//ruta que devuelve los detalles de la pelicula, en actores y categorias devuelve las ids de dichos actores y categorias
	@GetMapping(path = "/{id:\\d+}/details")//expresión genérica
	public FilmEditDTO getOneDetailed(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return FilmEditDTO.from(item.get());
	}
	
	@Operation(summary = "Listado de los actores de la pelicula")
	@ApiResponse(responseCode = "200", description = "Pelicula encontrada")
	@ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getFilms(
			@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getActors().stream().map(item -> ActorDTO.from(item)).toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmEditDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(FilmEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getFilmId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(FilmEditDTO.from(item));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
}
