package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	List<Actor> findByActorId(int id);
	List<Actor> findTop5ByFirstNameStartingWithOrderByLastName(String str);
}
