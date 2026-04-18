package com.fuga.assignment.exception;

import com.fuga.assignment.generated.model.AlbumServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FugaException.class)
	public ResponseEntity<AlbumServiceError> handleFugaException(FugaException ex) {
		AlbumServiceError serviceError = new AlbumServiceError();
		serviceError.setMessages(List.of(ex.getMessage()));
		serviceError.setErrorCode(ex.getCode().name());
		HttpStatus httpStatus = switch (ex.getCode()) {
			case D001, D002 -> HttpStatus.NOT_FOUND;
			case D003, D004 -> HttpStatus.BAD_REQUEST;
		};
		return ResponseEntity.status(httpStatus).body(serviceError);
	}
}
