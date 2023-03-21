package com.example.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class MyConfig {
	
	@Bean
	@Profile("default")
	UnaTonteria unaTonteria() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				// TODO Auto-generated method stub
				return "Dice una tonteria";
			}
		};
	}
	
	@Bean
	@Profile("test")
	UnaTonteria unaTonteriaTest() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				// TODO Auto-generated method stub
				return "Dice una tonteria para el test";
			}
		};
	}
	
	@Bean
	@Profile("prod")
	UnaTonteria unaTonteriaProd() {
		return new UnaTonteria() {
			
			@Override
			public String dimeAlgo() {
				// TODO Auto-generated method stub
				return "Dice una tonteria en producción";
			}
		};
	}
}
