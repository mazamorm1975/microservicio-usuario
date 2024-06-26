package com.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.models.Carro;
import com.usuario.models.Empleado;
import com.usuario.models.Motocicleta;
import com.usuario.models.Usuario;
import com.usuario.service.usuarioService;

@RestController
@RequestMapping("/usuario")
public class Controller {

	@Autowired
	private usuarioService userService;
	
		
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtieneUsuarioPorId(@PathVariable("id") int id){
		
		Usuario user1 =  userService.listarUsuarioPorId(id);
		
		if(user1 == null) {
			return new ResponseEntity<Usuario>(user1, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(user1, HttpStatus.OK);
	}
	
	
	@GetMapping("/listadoCompleto")
	public ResponseEntity<List<Usuario>> listadoCompleto(){
		
	   List<Usuario> listado =userService.getAll();
		
		return new ResponseEntity<>(listado, HttpStatus.OK);
	}
	
	
	@PostMapping("/guardar")
	public ResponseEntity<Usuario> registrar(@RequestBody Usuario user){
		
		Usuario user2 = userService.guardar(user);
		
		return new ResponseEntity<Usuario>(user2, HttpStatus.CREATED);
	}
	
	
	//Se genera API Rest para llamar al servicio que conectara con el microservice_coche
	@GetMapping("/coche/{usuarioId}")
	public ResponseEntity<List<Carro>> consigueVehiculosPorUsuarioId(@PathVariable("usuarioId") int usuarioId){
		
		Usuario user = userService.listarUsuarioPorId(usuarioId);
		
		if(user == null) {
			
			return ResponseEntity.notFound().build();
		}
		
	    List<Carro> listadoCompletoDeVehiculos = userService.obtenerListadoVehiculos(usuarioId);
		
		return new ResponseEntity<List<Carro>>(listadoCompletoDeVehiculos, HttpStatus.OK);		
	}
	
	@GetMapping("/moto/{usuarioId}")
	public ResponseEntity<List<Motocicleta>> reporteTotalMotosPorIdUsuario(@PathVariable("usuarioId") int usuarioId){
		
		Usuario usuario_1 = userService.listarUsuarioPorId(usuarioId);
		
		if(usuario_1 == null) {
			
			return ResponseEntity.notFound().build();
		}
		
	  List<Motocicleta> listadoCompletoPorUsuarioId = userService.obtenerListadoMotocicletasPorUsuarioID(usuarioId);
		
		return new ResponseEntity<List<Motocicleta>>(listadoCompletoPorUsuarioId, HttpStatus.OK);
	}
	
	
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCoche(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro car){
		
		Carro coche = userService.saveCarro(usuarioId, car);
		
		return new ResponseEntity<Carro>(coche, HttpStatus.CREATED);
	}
	
	@GetMapping("/carro_2/{carroId}")
	public ResponseEntity<List<Carro>> consultaPorIdCarro(@PathVariable("carroId") int carroId){
		
	List<Carro> car_final_info = userService.consultaCarroPorId(carroId);
	
	return new ResponseEntity<List<Carro>>(car_final_info, HttpStatus.OK);
	
	}
	
	@PostMapping("/guardarMoto/{motoUserId}")
	public ResponseEntity<Motocicleta> guardadoRegistroMotoConUserId(@PathVariable("motoUserId") int motoUserId, @RequestBody Motocicleta moto){
		
	   Motocicleta moto_final_info = userService.salvarMotosPorIdUsuario(motoUserId, moto);
		
		return new ResponseEntity<Motocicleta>(moto_final_info, HttpStatus.CREATED);
	}
	
	//Servicio Rest para ubicar a un empleado por id conectando con el microservicio-empleado
	//utilizando RestTemplate
	@GetMapping("/busquedaEmpleado/{id}")
	public ResponseEntity<Empleado> busquedaEmpleadoPorId(@PathVariable("id") int id){
	
	 Empleado empleado_report =	userService.obtenerEmpleadoPorId(id);
		
		return new ResponseEntity<Empleado>(empleado_report, HttpStatus.OK);
	}
	
	@GetMapping("/buscarEmpId/{id}")
	public ResponseEntity<Empleado> busquedaEmpleadoID(@PathVariable("id")int id){
		
	 Empleado emp =	userService.ubicarEmpleadoPorId(id);
	 
	 return new ResponseEntity<Empleado>(emp, HttpStatus.OK);
	}
	
}
