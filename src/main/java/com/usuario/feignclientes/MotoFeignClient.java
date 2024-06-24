package com.usuario.feignclientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.usuario.models.Motocicleta;

@FeignClient(name="microservicio-motocicleta", url="http://localhost:8002/motocicleta/guardar")
public interface MotoFeignClient {

	@PostMapping()
	public Motocicleta salvar(@RequestBody Motocicleta moto);
				
}
