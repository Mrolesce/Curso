package com.example.application.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;

@RestController
@RequestMapping(path = { "/api/lenguages/v1", "/api/lenguges" })
public class LanguageResource {
	
	@Autowired
	LanguageService srv;

	@GetMapping
	public List<Language> getAll(@RequestParam(required = false) String sort) {
		if(sort != null)
			return (List<Language>)srv.getByProjection(Sort.by(sort), Language.class);
		return srv.getByProjection(Language.class);
	}
	
	@GetMapping(path = "/{id:\\d+}")
	public List<Language> getOne(@RequestParam(required = false) String sort) {
		if(sort != null)
			return (List<Language>)srv.getByProjection(Sort.by(sort), Language.class);
		return srv.getByProjection(Language.class);
	}

}
