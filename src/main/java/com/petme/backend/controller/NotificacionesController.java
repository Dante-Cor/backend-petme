package com.petme.backend.controller;

import com.petme.backend.model.Notificaciones;
import com.petme.backend.model.TipoNotificacion;
import com.petme.backend.service.NotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionesController {
    private final NotificacionesService notificacionesService;

    @Autowired
    public NotificacionesController(NotificacionesService notificacionesService) {
        this.notificacionesService = notificacionesService;
    }

    // 1. POST: Se usa código 201 (CREATED)
    @PostMapping
    public ResponseEntity<Notificaciones> crear(
            @RequestParam Long userId,
            @RequestParam TipoNotificacion tipo,
            @RequestParam String titulo,
            @RequestParam String contenido) {

        Notificaciones nueva = notificacionesService.crearNotificacion(userId, tipo, titulo, contenido);
        // Devuelve el objeto + Código 201
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // 2. GET: Se usa código 200 (OK) estándar para consultas exitosas
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Notificaciones>> obtenerPorUsuario(@PathVariable Long userId) {
        List<Notificaciones> lista = notificacionesService.obtenerTodasPorUsuario(userId);
        // Código 200
        return ResponseEntity.ok(lista);
    }

    // 3. GET: Código 200 (OK)
    @GetMapping("/usuario/{userId}/sin-leer")
    public ResponseEntity<List<Notificaciones>> obtenerSinLeer(@PathVariable Long userId) {
        List<Notificaciones> lista = notificacionesService.obtenerSinLeerPorUsuario(userId);
        return ResponseEntity.ok(lista);
    }

    // 4. PUT: Se usa código 200 (OK) tras actualizar exitosamente
    @PutMapping("/{id}/leer")
    public ResponseEntity<Notificaciones> marcarLeida(@PathVariable Long id) {
        Notificaciones actualizada = notificacionesService.marcarComoLeida(id);
        return ResponseEntity.ok(actualizada);
    }

    // 5. PUT: Marcar todas como leídas (Actualización masiva)
    @PutMapping("/usuario/{userId}/leer-todas")
    public ResponseEntity<String> marcarTodasLeidas(@PathVariable Long userId) {
        notificacionesService.marcarTodasComoLeidas(userId);
        // Devolvemos un mensaje simple confirmando la acción
        return ResponseEntity.ok("Todas las notificaciones marcadas como leídas");
    }

    // --- MANEJO DE EXCEPCIONES ---
    // Este método "escucha" si ocurre un error de "No Encontrado" en cualquiera de los métodos de arriba
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException e) {
        // Devuelve un código 404 Not Found y el mensaje del error (ej: "Usuario no encontrado con ID: id")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
