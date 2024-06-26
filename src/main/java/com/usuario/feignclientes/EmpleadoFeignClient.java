package com.usuario.feignclientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.usuario.models.Empleado;

@FeignClient(name="microservice-empleado", url="http://localhost:8004/empleado/consultaPorId")
public interface EmpleadoFeignClient {

	
	@GetMapping("/{id}")
	public Empleado buscarEmpleadoPorId(@PathVariable("id") int id);	
			
	
}
