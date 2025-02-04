package com.usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.usuario.models.Carro;
import com.usuario.models.Empleado;
import com.usuario.models.Motocicleta;
import com.usuario.models.Usuario;


public interface UsuarioService {

	List<Carro> obtenerListadoVehiculos(int id);
	List<Motocicleta> obtenerListadoMotocicletasPorUsuarioID(int id);
	Motocicleta guardarMoto(int usuarioId, Motocicleta moto);
	Empleado obtenerEmpleadoPorId(int id);
	List<Carro> consultaCarroPorId(int carroId);
	Carro saveCarro(int usuarioId, Carro carro);
	Empleado ubicarEmpleadoPorId(int id);
	Motocicleta salvarMotosPorIdUsuario(int usuarioIdMoto, Motocicleta moto);
	Usuario listarUsuarioPorId(int id);
	Usuario guardar(Usuario usuario);
	List<Usuario> getAll();
}
