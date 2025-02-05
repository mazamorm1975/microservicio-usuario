package com.usuario.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.usuario.exceptions.ExcepcionesPersonalizadas;
import com.usuario.models.Carro;
import com.usuario.models.Empleado;
import com.usuario.models.Motocicleta;
import com.usuario.models.Usuario;
import com.usuario.service.UsuarioService;
import com.usuario.serviceImpl.UsuarioServiceImpl;

import feign.FeignException.NotFound;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuario")
public class Controller {

	Logger loger = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private Resilience4JCircuitBreakerFactory circuitBreakerFactory;

	@Autowired
	private UsuarioService userService;

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtieneUsuarioPorId(@PathVariable("id") int id) throws Exception {

		Usuario user1 = userService.listarUsuarioPorId(id);

		if (Objects.isNull(user1)) {
			throw new Exception("No fue posible la ubicación de este usuario en la base de datos");
		}

		return new ResponseEntity<Usuario>(user1, HttpStatus.OK);
	}

	@GetMapping("/listadoCompleto")
	public ResponseEntity<List<Usuario>> listadoCompleto() {

		List<Usuario> listado = userService.getAll();

		return new ResponseEntity<>(listado, HttpStatus.OK);
	}

	@PostMapping("/guardar")
	public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario user) throws Exception {

		Usuario user2 = userService.guardar(user);

		return new ResponseEntity<Usuario>(user2, HttpStatus.CREATED);
	}

	// Se genera API Rest para llamar al servicio que conectara con el
	// microservice_coche
	@GetMapping("/coche/{usuarioId}")
	public ResponseEntity<List<Carro>> consigueVehiculosPorUsuarioId(@PathVariable("usuarioId") int usuarioId) {

		Usuario user = userService.listarUsuarioPorId(usuarioId);

		if (user == null) {

			return ResponseEntity.notFound().build();
		}

		// El metodo obtenerListadoVehiculos(int id), conecta con el microservicio-coche
		// por medio de RestTemplate
		//lista de prueba
		List<Carro> listadoCompletoDeVehiculos = userService.obtenerListadoVehiculos(usuarioId);

		return new ResponseEntity<List<Carro>>(listadoCompletoDeVehiculos, HttpStatus.OK);
	}

	@GetMapping("/moto/{usuarioId}")
	public ResponseEntity<List<Motocicleta>> reporteTotalMotosPorIdUsuario(@PathVariable("usuarioId") int usuarioId) {

		Usuario usuario_1 = userService.listarUsuarioPorId(usuarioId);

		if (usuario_1 == null) {

			return ResponseEntity.notFound().build();
		}

		// El metodo obtenerListadoMotocicletasPorUsuarioID(int id), conecta con el
		// microservicio-motocicleta por medio de RestTemplate
		List<Motocicleta> listadoCompletoPorUsuarioId = userService.obtenerListadoMotocicletasPorUsuarioID(usuarioId);

		return new ResponseEntity<List<Motocicleta>>(listadoCompletoPorUsuarioId, HttpStatus.OK);
	}

	// El metodo guardarMoto(int usuarioId, Motocicleta moto) utiliza el proyecto
	// spring cloud feign para llamar al microservicio-motocicleta
	@PostMapping("/guardarMoto")
	public ResponseEntity<Motocicleta> guardarRegistroMoto(@RequestBody Motocicleta moto) {

		int usuarioId = moto.getUsuarioId();
		Motocicleta motoRegistration = userService.guardarMoto(usuarioId, moto);

		return new ResponseEntity<Motocicleta>(motoRegistration, HttpStatus.CREATED);
	}

	@PostMapping("/carro/{usuarioId}")
	@Retry(name = "carro-usuario")
	public ResponseEntity<Carro> guardarCoche(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro car)
			throws Exception {

		loger.info("En espera de response de petición HttpPost");

		if (car.getMarca().isEmpty() || car.getModelo().isEmpty()) {
			throw new Exception("No fue posible la inserción del registro");
		}

		return circuitBreakerFactory.create("carro").run(() -> microservicioCoche(usuarioId, car),
				throwable -> fallback());

	}

	public ResponseEntity<Carro> microservicioCoche(int usuarioId, Carro car) {

		Carro car2 = userService.saveCarro(usuarioId, car);

		return new ResponseEntity<Carro>(car2, HttpStatus.OK);
	}

	private ResponseEntity<Carro> fallback() {

		loger.info("No fue posible la inserción del registro");
		Carro car = new Carro();

		return new ResponseEntity<Carro>(car, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/carro_2/{carroId}")
	public ResponseEntity<List<Carro>> consultaPorIdCarro(@PathVariable("carroId") int carroId) {

		List<Carro> car_final_info = userService.consultaCarroPorId(carroId);

		return new ResponseEntity<List<Carro>>(car_final_info, HttpStatus.OK);

	}

	// IMPLEMENTACIÓN DEL PATRON SPRING CLOUD CIRCUIT-BREAKER

	@PostMapping("/guardarMoto/{motoUserId}")
	public ResponseEntity<Motocicleta> guardadoRegistroMotoConUserId(@PathVariable("motoUserId") int motoUserId,
			@RequestBody Motocicleta moto) {

		// Motocicleta moto_final_info = userService.salvarMotosPorIdUsuario(motoUserId,
		// moto);

		return circuitBreakerFactory.create("guardarMoto").run(() -> microservicioMotocicleta(motoUserId, moto),
				throwable -> metodoFallBack());
	}

	public ResponseEntity<Motocicleta> microservicioMotocicleta(int usuarioId, Motocicleta moto) {

		Motocicleta moto_final_result = userService.salvarMotosPorIdUsuario(usuarioId, moto);

		return new ResponseEntity<Motocicleta>(moto_final_result, HttpStatus.CREATED);

	}

	@GetMapping("/buscarEmpId/{id}")
	public ResponseEntity<Empleado> busquedaEmpleadoID(@PathVariable("id") int id) {

		Empleado emp = userService.ubicarEmpleadoPorId(id);

		return new ResponseEntity<Empleado>(emp, HttpStatus.OK);

	}

	private ResponseEntity<Motocicleta> metodoFallBack() {
		loger.info("No fue posible comunicación con el microservicio-motocicleta");
		Motocicleta moto = new Motocicleta(); 
		return new ResponseEntity<Motocicleta>(moto, HttpStatus.NO_CONTENT);
	}
	
	private static int restaNumeros()
	{
		return 30-20;
	}
	
	private static int metodoTest() {
		return 5+5;		
	}
}
