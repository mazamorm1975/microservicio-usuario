package com.usuario.exceptions;


import java.time.LocalDate;
import java.util.List;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesException {

	private LocalDate hora_excepcion;
	private String mensaje;
	private String detalle_excepcion;
		
	
	
}
