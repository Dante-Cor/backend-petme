package com.petme.backend.controller;

import com.petme.backend.exceptions.FotosMascotaNotFoundException;
import com.petme.backend.model.FotosMascota;
import com.petme.backend.service.FotosMascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fotos")
public class FotosMascotaController {
    //Inyectar clase
    private final FotosMascotaService fotosMascotaService;

    public FotosMascotaController(FotosMascotaService fotosMascotaService) {
        this.fotosMascotaService = fotosMascotaService;
    }

    // getFotos / GET / sin path
    @GetMapping
    public List<FotosMascota> allFotosMascotas() {
        return fotosMascotaService.getFotosMascotas();
    }

    // saveFoto / POST / path = "/new-foto"
    @PostMapping("/new-foto")
    public ResponseEntity<FotosMascota> saveFoto(@RequestBody FotosMascota nuevaFoto) {
        FotosMascota savedFoto = fotosMascotaService.createFotoMascota(nuevaFoto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFoto);
    }

    // getById / GET / "/id-foto/{id}"
    @GetMapping("/id-foto/{id}")
    public ResponseEntity<FotosMascota> getById(@PathVariable Long id) {
        try {
            FotosMascota foto = fotosMascotaService.findById(id);
            return ResponseEntity.ok(foto);
        } catch (FotosMascotaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // deleteById / DELETE / "/delete-foto/{id}" / 204 y 404
    @DeleteMapping("/delete-foto/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            fotosMascotaService.deleteFotoMascota(id);
            return ResponseEntity.noContent().build();
        } catch (FotosMascotaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // updateById / PUT / "/update-foto/{id}" / 201 y 404
    @PutMapping("/update-foto/{id}")
    public ResponseEntity<FotosMascota> updateById(@RequestBody FotosMascota foto, @PathVariable Long id) {
        try {
            FotosMascota updated = fotosMascotaService.updateFotoMascota(foto, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
        } catch (FotosMascotaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
