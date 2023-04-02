package com.example.applications.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.dtos.NovedadesDTO;

@Service
public class CatalogoServiceImpl implements CatalogoService  {
	
	@Autowired
	private FilmService filmSrv;
	
	@Autowired
	private ActorService actorSrv;
	
	@Autowired
	private CategoryService categorySrv;
	
	@Autowired
	private LanguageService languageSrv;
	
	@Override
	public NovedadesDTO novedades (Timestamp fecha){
		if(fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		return new NovedadesDTO();
	}

}
