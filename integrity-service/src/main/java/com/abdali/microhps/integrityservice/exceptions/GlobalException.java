package com.abdali.microhps.integrityservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.abdali.microhps.integrityservice.exceptions.model.IntegrityMessage;


@ControllerAdvice
public class GlobalException {
	
//	@ExceptionHandler
//	public ResponseEntity<ErrorMessage> handleResourceNotFoundException (ResourceNotFoundException ex) {
//		ErrorMessage eObject = new ErrorMessage();
//		eObject.setStatus(HttpStatus.NOT_FOUND.value());
//		eObject.setMessage(ex.getMessage()); 
//		return new ResponseEntity<ErrorMessage>(eObject, HttpStatus.NOT_FOUND);
//	}
//
//	@ExceptionHandler
//	public ResponseEntity<ErrorMessage> handleNoDataFoundException (NoDataFoundException ex) {
//		ErrorMessage eObject = new ErrorMessage();
//		eObject.setStatus(HttpStatus.NO_CONTENT.value());
//		eObject.setMessage(ex.getMessage());
//		return new ResponseEntity<ErrorMessage>(eObject, HttpStatus.OK);
//	}
//	
//	@ExceptionHandler
//	public ResponseEntity<ErrorMessage> handleInvalidEntityException (InvalidEntityException ex) {
//		ErrorMessage eObject = new ErrorMessage();
//		eObject.setStatus(HttpStatus.BAD_REQUEST.value());
//		eObject.setErrors(ex.getErrors());
//		eObject.setMessage(ex.getMessage());
//		return new ResponseEntity<ErrorMessage>(eObject, HttpStatus.OK);
//	}
	
	@ExceptionHandler
	public ResponseEntity<IntegrityMessage> handleMessageFormatException (IntegrityException ex) {
		IntegrityMessage eObject = new IntegrityMessage();
		eObject.setStatus(ex.getCode());
		eObject.setMessage(ex.getMessage());
		return new ResponseEntity<IntegrityMessage>(eObject, HttpStatus.OK);
	}
}
