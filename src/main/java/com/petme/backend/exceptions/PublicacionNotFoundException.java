package com.petme.backend.exceptions;

public class PublicacionNotFoundException extends RuntimeException {
    public PublicacionNotFoundException(Long id) {
        super("Publicación no encontrada con ID: " + id);
    }
}
