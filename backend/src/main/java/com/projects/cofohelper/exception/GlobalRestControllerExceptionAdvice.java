package com.projects.cofohelper.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerExceptionAdvice {
//	 TODO: 2020-07-02 익셉션어드바이스 작성해야 함.
	@ExceptionHandler(RuntimeException.class)
	public String notFoundToken(Exception e) {
		return e.getMessage();
	}
}
