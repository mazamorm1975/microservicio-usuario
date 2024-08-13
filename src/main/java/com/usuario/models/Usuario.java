package com.usuario.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="Usuario.findByUserId", query="SELECT u FROM Usuario u WHERE u.id=:userId")
public class Usuario { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=2,message="El campo nombre no puede ir en vacio")
	@Column(name="nombre")
	private String nombre;
	
	@Size(min=2,message="El campo e-mail no puede ir en vacio")
	@Column(name="email")
	private String email;
	
	
}
