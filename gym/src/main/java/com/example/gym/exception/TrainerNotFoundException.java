package com.example.gym.exception;

public class TrainerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TrainerNotFoundException(String message) {
        super(message);
    }
}
