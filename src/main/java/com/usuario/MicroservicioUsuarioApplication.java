package com.usuario;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

@EnableFeignClients
@SpringBootApplication
public class MicroservicioUsuarioApplication {

	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
	    return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MicroservicioUsuarioApplication.class, args);
	}

}
