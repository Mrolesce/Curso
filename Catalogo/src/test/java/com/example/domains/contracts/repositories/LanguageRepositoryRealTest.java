package com.example.domains.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("test")
public class LanguageRepositoryRealTest {
	
	@Autowired
	LanguageRepository dao;
	
	@Test
	void testFindAll() {
		assertThat(dao.findAll().size()).isGreaterThanOrEqualTo(6);
	}

}
