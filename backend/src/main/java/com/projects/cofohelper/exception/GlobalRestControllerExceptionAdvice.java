package com.projects.cofohelper.exception;

import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.net.URISyntaxException;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.projects.cofohelper.dto.response.ResponseDataDto;
import com.projects.cofohelper.exception.alreadyexist.AlreadyExistException;
import com.projects.cofohelper.exception.notfound.NotFoundException;

@RestControllerAdvice
public class GlobalRestControllerExceptionAdvice {
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ResponseDataDto> notFoundToken(Exception e) {
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.FORBIDDEN.value(), e.toString()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseDataDto> notFound(NotFoundException e) {
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}
	
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<ResponseDataDto> alreadyExist(AlreadyExistException e) {
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<ResponseDataDto> unAuthorized(UnAuthorizedException e){
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResponseDataDto> entityNotFound(EntityNotFoundException e){
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
	}
	
	@ExceptionHandler(URISyntaxException.class)
	public ResponseEntity<ResponseDataDto> invalidProblemUri(Exception e){
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.BAD_REQUEST.value(), "invalid problem uri"+e.getMessage()));
	}
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ResponseDataDto> invalidProblemName(Exception e){
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.BAD_REQUEST.value(), "invalid problem name"+e.getMessage()));
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ResponseDataDto> failIO(Exception e){
		return ResponseEntity.badRequest()
				.body(new ResponseDataDto(HttpStatus.BAD_REQUEST.value(), "fail io"+e.getMessage()));
	}
}
