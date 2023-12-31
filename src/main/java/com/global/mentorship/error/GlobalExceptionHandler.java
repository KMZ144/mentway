package com.global.mentorship.error;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.stripe.exception.StripeException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	
	public ResponseEntity<Error> handleRecordNotFoundException(EntityNotFoundException ex){
		Error error =  new Error(ex.getMessage(),new HashMap<>());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex){
		Error error = new Error("validation error",new HashMap<>());
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			error.addError(fieldName, errorMessage);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

	}
	
	@ExceptionHandler(NotAvaliableTimeException.class)
	public ResponseEntity<Error> handleNotAvaliableTimeException(NotAvaliableTimeException ex){
		Error error = new Error("concurrency error",new HashMap<>());
		error.addError("date", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Error> handleBadCredentialsException(BadCredentialsException ex){
		Error error = new Error("Bad Credntials",new HashMap<>());
		error.addError("date", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	@ExceptionHandler(NotValidMentorException.class)
	public ResponseEntity<Error> handleBadCredentialsException(NotValidMentorException ex){
		Error error = new Error("not valid mentor",new HashMap<>());
		error.addError("vadility", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		
	}
	
	@ExceptionHandler(NoPayemntMethodException.class)
	public ResponseEntity<Error> handleNoPaymentException(NoPayemntMethodException ex){
		Error error = new Error("no payment",new HashMap<>());
		error.addError("payment", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	@ExceptionHandler(PaymentMethodAlreadyExist.class)
	public ResponseEntity<Error> handlePaymentMethodAlreadyExist(PaymentMethodAlreadyExist ex){
		Error error = new Error("payment",new HashMap<>());
		error.addError("payment", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
	
	@ExceptionHandler(StripeException.class)
	public ResponseEntity<Error> handleStripeException(StripeException ex){
		Error error = new Error("Stripe Exception",new HashMap<>());
		error.addError("payment", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(NotValidPaymentException.class)
	public ResponseEntity<Error> handleNotValidPaymentException(NotValidPaymentException ex){
		Error error = new Error("Stripe Exception",new HashMap<>());
		error.addError("payment", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(DuplicatedUserException.class)
	public ResponseEntity<Error> handleDuplicatedUserException(DuplicatedUserException ex){
		Error error = new Error("register Exception",new HashMap<>());
		error.addError("register", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
}
