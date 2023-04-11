package com.example.application.resources;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.applications.services.CatalogoService;
import com.example.domains.entities.dtos.NovedadesDTO;

@RestController
@RequestMapping(path = { "/api/catalogo/v1", "/api/catalogo" })
public class CatalogoResource {

	@Autowired
	CatalogoService srv;
	
	@GetMapping(path = "/novedades")
	public NovedadesDTO novedades(@RequestParam(required = false) Timestamp time) {
		return srv.novedades(time);
	}

}
