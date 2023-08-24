package com.api.app.service.handler;

import com.api.app.model.exception.ApiException;
import com.api.app.model.exception.BadRequestException;
import com.api.app.model.exception.ForbiddenException;
import com.api.app.model.exception.InternalSeverException;
import com.api.app.model.exception.NotFoundException;
import com.api.app.model.exception.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternalToModelException {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> badRequestException(BadRequestException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> notFoundException(NotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<?> forbiddenException(ForbiddenException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotImplementedException.class)
  public ResponseEntity<?> notImplementedException(NotImplementedException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
  }

  @ExceptionHandler(InternalSeverException.class)
  public ResponseEntity<?> internalServerException(InternalSeverException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<?> internalError(ApiException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
