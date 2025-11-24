package com.petme.backend.controller;

import com.petme.backend.model.Adopcion;
import com.petme.backend.service.AdopcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/adopciones")
@CrossOrigin(origins = "*")
public class AdopcionController {

    private final AdopcionService adopcionService;

    // ----- Constructor con Inyección de Dependencias
    @Autowired
    public AdopcionController(AdopcionService adopcionService) {
        this.adopcionService = adopcionService;
    }

    // 1. OBTENER TODAS LAS SOLICITUDES DE ADOPCIÓN (GET /api/v1/adopciones)
    @GetMapping
    public ResponseEntity<List<Adopcion>> getAllAdoptions() {
        List<Adopcion> adopciones = adopcionService.getAllAdoptions();
        return ResponseEntity.ok(adopciones);
    }

    // 2. CREAR UNA NUEVA SOLICITUD DE ADOPCIÓN (POST /api/v1/adopciones/solicitar)
    @PostMapping("/solicitar")
    public ResponseEntity<Adopcion> createAdoption(@RequestBody Adopcion nuevaAdopcion) {
        // El servicio establece la fecha de solicitud y el estado a "PENDIENTE"
        Adopcion adopcionCreada = adopcionService.createAdoption(nuevaAdopcion);
        // Devuelve el objeto creado con el código 201
        return ResponseEntity.status(HttpStatus.CREATED).body(adopcionCreada);
    }

    // 3. OBTENER SOLICITUD POR ID (GET /api/v1/adopciones/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Adopcion> getAdoptionById(@PathVariable Long id) {
        try {
            Adopcion adopcion = adopcionService.getAdoptionById(id);
            return ResponseEntity.ok(adopcion);
        } catch (RuntimeException e) {
            // Manejo simple para cuando la solicitud no se encuentra
            return ResponseEntity.notFound().build(); // Devuelve 404 NOT FOUND
        }
    }

    // 4. OBTENER SOLICITUDES POR ESTADO (GET /api/v1/adopciones/estado/{estado})
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Adopcion>> getAdoptionsByStatus(@PathVariable String estado) {
        List<Adopcion> adopciones = adopcionService.getAdoptionsByStatus(estado);
        // Devolver 200 OK, incluso si la lista está vacía
        return ResponseEntity.ok(adopciones);
    }

    // 5. ACTUALIZAR EL ESTADO Y COMENTARIOS DE UNA SOLICITUD (PUT /api/v1/adopciones/actualizar/{id})
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Adopcion> updateAdoptionStatus(
            @PathVariable Long id,
            // Recibimos un mapa o un DTO simple con el nuevo estado y comentarios
            @RequestBody Adopcion statusUpdateData) {
        try {
            Adopcion adopcionActualizada = adopcionService.updateAdoptionStatus(
                    id,
                    statusUpdateData.getEstado(),
                    statusUpdateData.getComments()
            );
            // Devuelve 200 OK con la entidad actualizada
            return ResponseEntity.ok(adopcionActualizada);
        } catch (RuntimeException e) {
            // Manejo para cuando la solicitud no se encuentra
            return ResponseEntity.notFound().build(); // Devuelve 404 NOT FOUND
        }
    }

    // 6. ELIMINAR UNA SOLICITUD (DELETE /api/v1/adopciones/eliminar/{id})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteAdoption(@PathVariable Long id) {
        try {
            adopcionService.deleteAdoption(id);
            // Devuelve 204 NO CONTENT (Éxito sin contenido de respuesta)
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Manejo para cuando la solicitud no se encuentra
            return ResponseEntity.notFound().build(); // Devuelve 404 NOT FOUND
        }
    }
}