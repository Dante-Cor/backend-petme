package com.petme.backend.exceptions;

public class FotosMascotaNotFoundException extends RuntimeException {
    public FotosMascotaNotFoundException(Long id) {
        super("No se encontró la foto de mascota con id: " + id);
    }
}
