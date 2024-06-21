package com.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.models.Carro;
import com.usuario.models.Moto;
import com.usuario.models.Usuario;
import com.usuario.repository.usuarioRepository;

@Service
public class usuarioService {

	@Autowired
	private usuarioRepository usuarioRepo;
	
	
	@Autowired
	private RestTemplate restTemplate;

	
	public List<Carro> obtenerListadoVehiculos(int id){
		
		List<Carro> listadoTotal = restTemplate.getForObject("http://localhost:8003/coche/busquedaPorUsuarioId/"+id, List.class);
	
		return listadoTotal;
	}
	
	public List<Moto> obtenerListadoMotocicletasPorUsuarioID(int id){
		
	  List<Moto> listadoMotosIdUser =restTemplate.getForObject("http://localhost:8002/motocicleta/obtenerTotalMotosPorUsuario/"+id, List.class);
	
	  return listadoMotosIdUser;
	}
	
	
	public Usuario listarUsuarioPorId(int id) {
	  	
		return usuarioRepo.findById(id).orElse(null);
	

	}
	
	public Usuario guardar(Usuario usuario) {
		
		Usuario user1 = usuarioRepo.save(usuario);
		
		return user1;				
	}
	
	public List<Usuario> getAll(){
		
		List<Usuario> listadoCompletoDeUsuarios = usuarioRepo.findAll();
		
		return listadoCompletoDeUsuarios;
		
	}
	

}
