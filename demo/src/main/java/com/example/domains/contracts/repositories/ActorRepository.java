package com.example.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Actor;

@RestResource(exported = true)//excluye al repositorio de la generacion spring rest
@RepositoryRestResource(path = "actores", itemResourceRel = "actor", collectionResourceRel = "actores")
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {

}
