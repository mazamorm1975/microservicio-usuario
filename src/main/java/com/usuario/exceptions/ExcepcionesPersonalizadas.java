package com.usuario.exceptions;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties.Oracle;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExcepcionesPersonalizadas extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> capturaTodasLasExcepciones(Exception ex, WebRequest request) throws Exception {
		DetallesException userNotFoundException = new DetallesException(LocalDate.now(), ex.getMessage(),
				request.getDescription(false));
		userNotFoundException.getHora_excepcion();
		userNotFoundException.getMensaje();
		userNotFoundException.getDetalle_excepcion();

		return new ResponseEntity<Object>(userNotFoundException, HttpStatus.NOT_FOUND);

	}

	@Nullable
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		DetallesException errores = new DetallesException(

				LocalDate.now(),
				"Total Errores : " + ex.getErrorCount() + "  Descripción : " + ex.getFieldError().getDefaultMessage(),
				request.getDescription(false));

		return new ResponseEntity<Object>(errores, HttpStatus.BAD_REQUEST);
	}

}
