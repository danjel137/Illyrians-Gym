package com.example.gym.exception;

public class GymNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GymNotFoundException(String message) {
        super(message);
    }
}
