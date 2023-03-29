package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;

import lombok.Value;

//Data para clases mutables, Value para clasrs immutables
@Value
public class ActorDTO {

	private int actorId;
	private String firstName;
	private String lastName;
	
	public static ActorDTO from(Actor target) {	
		return new ActorDTO(target.getActorId(), target.getFirstName(), target.getLastName());
	}
	public static Actor from(ActorDTO target) {	
			return new Actor(target.getActorId(), target.getFirstName(), target.getLastName());
		}
}
