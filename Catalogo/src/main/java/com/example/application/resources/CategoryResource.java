package com.example.application.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;

@RestController
@RequestMapping(path = { "/api/categorias/v1", "/api/categorias" })
public class CategoryResource {

	@Autowired
	CategoryService srv;
	
	@GetMapping
	public List<Category> getAll(@RequestParam(required = false) String sort) {
		if(sort != null)
			return (List<Category>) srv.getByProjection(Sort.by(sort), Category.class);
		return srv.getByProjection(Category.class);
	}

}
