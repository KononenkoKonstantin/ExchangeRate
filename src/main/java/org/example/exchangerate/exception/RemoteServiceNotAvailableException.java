package org.example.exchangerate.exception;

public class RemoteServiceNotAvailableException extends RuntimeException {
    public RemoteServiceNotAvailableException(String message) {
        super(message);
    }
}
