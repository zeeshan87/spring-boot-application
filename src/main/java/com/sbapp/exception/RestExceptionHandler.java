package com.sbapp.exception;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Added a global exception handler to encapsulate
 * all exceptions and throw our messages in a body. 
 */

@ControllerAdvice
public class RestExceptionHandler {
	private static final Log LOG = LogFactory.getLog(RestExceptionHandler.class);
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> exceptionEntityNotFoundHandler(EntityNotFoundException ex) {
		LOG.warn("EntityNotFoundException occurred: " + ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> exceptionMethodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
		StringBuilder messages = new StringBuilder();		
		ex.getBindingResult().getAllErrors().forEach(entry -> messages.append(entry.getDefaultMessage() + "\n"));
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(messages.toString());
		LOG.warn("MethodArgumentNotValidException occurred: " + error.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> exceptionMethodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException ex) {
		LOG.warn("MethodArgumentTypeMismatchException occurred " + ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage("Invalid path parameter");		
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> exceptionHttpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
		LOG.warn("HttpRequestMethodNotSupportedException occurred " + ex.getMessage());
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.METHOD_NOT_ALLOWED.value());
		error.setMessage("This method is not allowed for this resource");		
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		LOG.error("Exception occurred", ex);
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("The request could not be completed at this time. Please try again later");		
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}