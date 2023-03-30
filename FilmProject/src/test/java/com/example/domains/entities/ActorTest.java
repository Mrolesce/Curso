package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ActorTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Nested
	class OK{
		@Test
		void actorTest() {
			var actor = new Actor(0, null, null);
		}
	}
	
	

}
