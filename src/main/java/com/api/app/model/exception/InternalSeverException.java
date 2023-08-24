package com.api.app.model.exception;

public class InternalSeverException extends ApiException {
  public InternalSeverException(String message) {
    super(message, ExceptionType.SERVER_EXCEPTION);
  }
}
