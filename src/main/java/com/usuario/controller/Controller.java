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

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.usuario.models.Carro;
import com.usuario.models.Moto;
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
	public ResponseEntity<List<Moto>> reporteTotalMotosPorIdUsuario(@PathVariable("usuarioId") int usuarioId){
		
		Usuario usuario_1 = userService.listarUsuarioPorId(usuarioId);
		
		if(usuario_1 == null) {
			
			return ResponseEntity.notFound().build();
		}
		
	  List<Moto> listadoCompletoPorUsuarioId = userService.obtenerListadoMotocicletasPorUsuarioID(usuarioId);
		
		return new ResponseEntity<List<Moto>>(listadoCompletoPorUsuarioId, HttpStatus.OK);
	}
	
}
