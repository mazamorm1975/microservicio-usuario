package com.usuario.service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.feignclientes.CarroFeignClient;
import com.usuario.feignclientes.EmpleadoFeignClient;
import com.usuario.feignclientes.MotoFeignClient;
import com.usuario.models.Carro;
import com.usuario.models.Empleado;
import com.usuario.models.Motocicleta;
import com.usuario.models.Usuario;
import com.usuario.repository.usuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import reactor.core.publisher.Mono;

@Service
public class usuarioService {
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	private CarroFeignClient carroFeignCliente;
	
	@Autowired
	private EmpleadoFeignClient empleadoFeignClient;
	
	
	@Autowired
	private MotoFeignClient motoFeignClient;
	
	
	@Autowired
	private usuarioRepository usuarioRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//Metodo que relaciona el total de carros que pertenecen al usuario ID que se le pasa como parametro utilizando RestTemplate	
	public List<Carro> obtenerListadoVehiculos(int id){
		
		List<Carro> listadoTotal = restTemplate.getForObject("http://localhost:8003/coche/busquedaPorUsuarioId/"+id, List.class);
	
		return listadoTotal;
	}
	
	
	//Metodo que relaciona el total de motos que pertenecen al usuario ID que se le pasa como parametro utilizando RestTemplate	
	public List<Motocicleta> obtenerListadoMotocicletasPorUsuarioID(int id){
		
	  List<Motocicleta> listadoMotosIdUser =restTemplate.getForObject("http://localhost:8002/motocicleta/obtenerTotalMotosPorUsuario/"+id, List.class);
	
	  return listadoMotosIdUser;
	}
	
<<<<<<< HEAD
	
	//Metodo para guardar un registro de motocicleta llamando el microservicio-motocicleta con RestTemplate
	public Motocicleta guardarMoto(int usuarioId, Motocicleta moto) {
		
		moto.setUsuarioId(usuarioId);
		Motocicleta moto1 = motoFeignClient.salvar(moto);		
		
				
		return moto1;
	}
	
		
=======
	//Conexion con el microservicio-empleado pasando como parametro el id del empleado
	public Empleado obtenerEmpleadoPorId(int id){
		
		Empleado moto = restTemplate.getForObject("http://localhost:8004/empleado/consultaPorId/"+id, Empleado.class);
		
		return moto;
	}
	
>>>>>>> 1f1fdd4ede61231994f6a757f918f2d9f5003915
	//Metodo que realiza la conexion con microservicio-coche para consulta por id de usuario
	public List<Carro> consultaCarroPorId(int carroId) {
		
		 List<Carro> car_info =  restTemplate.getForObject("http://localhost:8003/coche/"+carroId, List.class);
		   
		 return car_info;
	}		
	
	
	public Carro saveCarro(int usuarioId, Carro carro) {
		
		carro.setUsuarioId(usuarioId);
		Carro car = carroFeignCliente.save(carro);
				
		return car;		
		
	}
	
	
<<<<<<< HEAD
=======
	public Empleado ubicarEmpleadoPorId(int id) {
		
	  Empleado em =	empleadoFeignClient.buscarEmpleadoPorId(id);
	
	return em;
	}
>>>>>>> 1f1fdd4ede61231994f6a757f918f2d9f5003915
	
	public Motocicleta salvarMotosPorIdUsuario(int usuarioIdMoto, Motocicleta moto) {
		moto.setUsuarioId(usuarioIdMoto);
		
		Motocicleta moto_info = motoFeignClient.salvar(moto);
		
		return moto_info;
		
	}
	
	
	
	public Usuario listarUsuarioPorId(int id) {
	  	
		Query nameQuery = (Query) entityManager.getEntityManagerFactory().createEntityManager().createNamedQuery("Usuario.findByUserId");
		nameQuery.setParameter("userId", id);
		
		return (Usuario) nameQuery.getSingleResult();
		//return usuarioRepo.findById(id).orElse(null);
	}
	
	
	
	private EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
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
