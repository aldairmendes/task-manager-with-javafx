package com.mendes15.taskmanagerwithjavafx.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, int errorCode) {
        super("[" + errorCode + "] " + message);
    }
}