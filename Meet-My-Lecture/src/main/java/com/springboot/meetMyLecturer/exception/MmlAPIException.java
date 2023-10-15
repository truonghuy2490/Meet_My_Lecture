package com.springboot.meetMyLecturer.exception;

import org.springframework.http.HttpStatus;

public class MmlAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public MmlAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MmlAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
