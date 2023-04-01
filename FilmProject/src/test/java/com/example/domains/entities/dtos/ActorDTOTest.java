package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;

class ActorDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void actorToActorDTO() {
		var actor = new Actor(0, "Marc", "ROLES");
		
		var actorDTO = ActorDTO.from(actor);
		
		assertEquals(ActorDTO.class, actorDTO.getClass());
	}
	
	@Test
	void actorDTOToActor() {
		var actorDTO = new ActorDTO(0, "Marc", "ROLES");
		
		var actor = ActorDTO.from(actorDTO);
		
		assertEquals(Actor.class, actor.getClass());
	}

}
