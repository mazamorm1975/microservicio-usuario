package com.usuario.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="datos_generales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nombreEmpleado")
	private String nombreEmpleado;
	
	@Column(name="domicilio")
	private String domicilio;
	
	@Column(name="telefonoEmpleado")
	private String telefonoEmpleado;
	
	@Column(name="usuarioId")
	private int usuarioId;
	
}
