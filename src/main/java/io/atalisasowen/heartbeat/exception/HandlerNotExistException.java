package io.atalisasowen.heartbeat.exception;

public class HandlerNotExistException extends RuntimeException{
    public HandlerNotExistException(String message) {
        super(message);
    }
}
