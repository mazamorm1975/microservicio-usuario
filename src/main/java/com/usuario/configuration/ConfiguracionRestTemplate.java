package com.usuario.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class ConfiguracionRestTemplate {

	@Bean
	public RestTemplate restTemplateConfiguration() {

		return new RestTemplate();
	}
	

}
