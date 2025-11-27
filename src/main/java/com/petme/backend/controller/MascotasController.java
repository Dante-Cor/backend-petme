package com.petme.backend.controller;

import com.petme.backend.model.Mascotas;
import com.petme.backend.service.MascotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
@CrossOrigin(origins = "*")
public class MascotasController {
    private final MascotasService mascotasService;

    @Autowired
    private MascotasController(MascotasService mascotasService){
        this.mascotasService = mascotasService;
    }

    @GetMapping
    public List<Mascotas> getMascotas(){
        return mascotasService.getAllMascotas();
    }

    // --- NUEVO MÉTODO POST ---
    // URL: POST /api/v1/mascotas?userId=1
    @PostMapping
    public ResponseEntity<Mascotas> saveMascota(
            @RequestBody Mascotas nuevaMascota, // Los datos del perro/gato
            @RequestParam Long userId) {        // El ID del dueño

        Mascotas mascotaGuardada = mascotasService.createMascota(nuevaMascota, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaGuardada);
    }
    // --- NUEVO MÉTODO GET Traer por Usuario ---
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Mascotas>> getByUserId(@PathVariable Long userId) {
        List<Mascotas> lista = mascotasService.getByUserId(userId);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Mascotas> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {

        Mascotas mascota = mascotasService.findById(id); // Usamos findById directamente
        // (Si no tienes findById en el servicio, usa mascotasRepository.findById(id).orElseThrow(...))

        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }

        mascota.setEstado_adopcion(estado);

        // Usamos el nuevo método que solo guarda, sin pedir ID de usuario
        Mascotas actualizada = mascotasService.actualizarMascota(mascota);

        return ResponseEntity.ok(actualizada);
    }

    //Método para buscar
    @GetMapping("/buscar")
    public ResponseEntity<List<Mascotas>> buscarMascotasFiltradas(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String tamano,
            @RequestParam(required = false) Integer edad) {

        List<Mascotas> resultados = mascotasService.buscarMascotasFiltradas(especie, tamano, edad);
        return ResponseEntity.ok(resultados);
    }

}
