package com.usuario.feignclientes;


import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.usuario.models.Carro;

import reactor.core.publisher.Mono;

@FeignClient(name="microservicio-coche", url="http://localhost:8003/coche/ingresar")
public interface CarroFeignClient {

	@PostMapping()
	public Carro save(@RequestBody Carro carro);
	
}
