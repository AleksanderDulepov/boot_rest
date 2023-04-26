package com.dulepov.spring.rest.boot_rest.exception_handling;

public class NoSuchEmployeeException extends RuntimeException{

    public NoSuchEmployeeException(String message) {
        super(message);
    }
}
