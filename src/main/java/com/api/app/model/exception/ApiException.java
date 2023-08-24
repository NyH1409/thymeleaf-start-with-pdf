package com.api.app.model.exception;

public class ApiException extends RuntimeException{
    private ExceptionType type;
    public ApiException(String message){
        super(message);
    }

    public ApiException(String message, ExceptionType type){
        super(message);
        this.type = type;
    }

    public enum ExceptionType{
        CLIENT_EXCEPTION, SERVER_EXCEPTION
    }
}
