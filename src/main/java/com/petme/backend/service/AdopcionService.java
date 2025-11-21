package com.petme.backend.service;

import com.petme.backend.model.Adopcion;
import com.petme.backend.repository.AdopcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdopcionService {


    private final AdopcionRepository adopcionRepository;

    @Autowired
    public AdopcionService(AdopcionRepository adopcionRepository) {
        this.adopcionRepository = adopcionRepository;
    }

    // Metodo para realizar una solicitud de adopcion
    public Adopcion createAdoption(Adopcion newAdopcion) {

        if (newAdopcion.getDate_Application() == null) {
            newAdopcion.setDate_Application(LocalDateTime.now());
        }
        //  Establece el estado inicial a "PENDIENTE".
        newAdopcion.setEstado("PENDIENTE");

        return adopcionRepository.save(newAdopcion);
    }

    //  Metodo para leer las solicitudes
    public List<Adopcion> getAllAdoptions() {
        return adopcionRepository.findAll();
    }

    // Metodo para buscar una solicitud por id
    public Adopcion getAdoptionById(Long id) {
        return adopcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud de Adopción no encontrada con ID: " + id));
    }

    // Metodo buscar solicitudes por tipo de estado
    public List<Adopcion> getAdoptionsByStatus(String estado) {
        return adopcionRepository.findByEstado(estado);
    }

    // Metdodo para actualizar el estado de la solicitud
    public Adopcion updateAdoptionStatus(Long id, String newStatus, String comments) { // <-- Cambiado el tipo de retorno
        return adopcionRepository.findById(id)
                .map(adoptionToUpdate -> {
                    adoptionToUpdate.setEstado(newStatus);
                    adoptionToUpdate.setComments(comments);

                    // Si se aprueba, establece la fecha de autorización
                    if ("APROBADA".equalsIgnoreCase(newStatus)) {
                        adoptionToUpdate.setDate_authorization(LocalDateTime.now());
                    } else {
                        // Si el estado es otro, se limpia o se ignora la fecha de autorización
                        adoptionToUpdate.setDate_authorization(null);
                    }

                    return adopcionRepository.save(adoptionToUpdate);
                })
                .orElseThrow(() -> new RuntimeException("Solicitud de Adopción no encontrada con ID: " + id));
    }

    // Metodo para eliminar una solicitud
    public void deleteAdoption(Long id) {
        if (!adopcionRepository.existsById(id)) {
            throw new RuntimeException("Solicitud de Adopción no encontrada con ID: " + id);
        }
        adopcionRepository.deleteById(id);
    }
}