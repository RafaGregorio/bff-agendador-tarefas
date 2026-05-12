package com.rafael.bffagendadortarefas.infrastructure.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
