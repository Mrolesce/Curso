package com.example.domains.contracts.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class ActorRepositoryMemoryTest {

	@Autowired
	private TestEntityManager em;
	
	@Autowired
	ActorRepository dao;
//	
//	@BeforeEach
//	void setUp() throws Exception {
//		var item = new Actor(0, "Pepito", "GRILLO");
//		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
//		em.persist(item);
//		item = new Actor(0, "Carmelo", "COTON");
//		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
//		em.persist(item);
//		item = new Actor(0, "Capitan", "TAN");
//		item.setLastUpdate(Timestamp.valueOf("2019-01-01 00:00:00"));
//		em.persist(item);
//	}

//	@Test
//	@Disabled
//	void testAll() {
//		assertEquals(3, dao.findAll().size());
//	}
//	
//	@Test
//	@Disabled
//	void testOne() {
//		var item = dao.getById(1);
//		
//		assertNotNull(item);
//		assertEquals("Pepito", item.getFirstName());
//	}
//	
//	@Test
//	@Disabled
//	void testSave() {
//		var item = dao.save(new Actor(0, "Marc", "Roles"));
//		
//		assertNotNull(item);
//		assertEquals(4, item.getActorId());
//	}

}
