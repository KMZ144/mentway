package com.global.mentorship.error;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	
	public ResponseEntity<Error> handleRecordNotFoundException(EntityNotFoundException ex){
		Error error =  new Error(ex.getMessage(),Arrays.asList(ex.getLocalizedMessage()));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}
}
