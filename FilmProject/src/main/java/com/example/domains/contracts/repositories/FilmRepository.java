package com.example.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>, RepositoryWithProjections{

}
