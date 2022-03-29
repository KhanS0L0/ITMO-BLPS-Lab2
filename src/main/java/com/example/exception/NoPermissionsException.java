package com.example.exception;

public class NoPermissionsException extends Exception {
    public NoPermissionsException(String message) {
        super(message);
    }
}
