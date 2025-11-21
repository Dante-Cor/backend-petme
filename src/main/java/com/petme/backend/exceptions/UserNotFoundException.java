package com.petme.backend.exceptions;

public class UserNotFoundException extends RuntimeException {
    // Constructor de la Exception
    public UserNotFoundException(Long id) {
        super("No se encuentra el User con id: " + id);
    }
}
