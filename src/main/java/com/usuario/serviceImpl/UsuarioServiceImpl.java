package com.usuario.serviceImpl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.usuario.service.UsuarioService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UsuarioServiceImpl implements UsuarioService {

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

	@Override
	public List<Carro> obtenerListadoVehiculos(int id) {
		List<Carro> listadoTotal = restTemplate.getForObject("http://localhost:8003/coche/busquedaPorUsuarioId/" + id,
				List.class);
		return listadoTotal;
	}

	@Override
	public List<Motocicleta> obtenerListadoMotocicletasPorUsuarioID(int id) {
		List<Motocicleta> listadoMotosIdUser = restTemplate
				.getForObject("http://localhost:8002/motocicleta/obtenerTotalMotosPorUsuario/" + id, List.class);

		return listadoMotosIdUser;

	}

	@Override
	public Motocicleta guardarMoto(int usuarioId, Motocicleta moto) {

		moto.setUsuarioId(usuarioId);
		Motocicleta moto1 = motoFeignClient.salvar(moto);

		return moto1;
	}

	@Override
	public Empleado obtenerEmpleadoPorId(int id) {

		Empleado moto = restTemplate.getForObject("http://localhost:8004/empleado/consultaPorId/" + id, Empleado.class);

		return moto;
	}

	@Override
	public List<Carro> consultaCarroPorId(int carroId) {
		List<Carro> car_info = restTemplate.getForObject("http://localhost:8003/coche/" + carroId, List.class);

		return car_info;
	}

	@Override
	public Carro saveCarro(int usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro car = carroFeignCliente.save(carro);

		return car;
	}

	@Override
	public Empleado ubicarEmpleadoPorId(int id) {

		Empleado em = empleadoFeignClient.buscarEmpleadoPorId(id);

		return em;
	}

	@Override
	public Motocicleta salvarMotosPorIdUsuario(int usuarioIdMoto, Motocicleta moto) {
		moto.setUsuarioId(usuarioIdMoto);

		Motocicleta moto_info = motoFeignClient.salvar(moto);

		return moto_info;
	}

	@Override
	public Usuario listarUsuarioPorId(int id) {
		Query nameQuery = (Query) entityManager.getEntityManagerFactory().createEntityManager()
				.createNamedQuery("Usuario.findByUserId");
		nameQuery.setParameter("userId", id);

		return (Usuario) nameQuery.getSingleResult();
		// return usuarioRepo.findById(id).orElse(null);
	}

	@Override
	public Usuario guardar(Usuario usuario) {
		Usuario user1 = usuarioRepo.save(usuario);

		return user1;

	}

	@Override
	public List<Usuario> getAll() {
		List<Usuario> listadoCompletoDeUsuarios = usuarioRepo.findAll();

		return listadoCompletoDeUsuarios;

	}

}
