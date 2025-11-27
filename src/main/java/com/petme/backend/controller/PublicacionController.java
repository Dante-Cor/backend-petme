package com.petme.backend.controller;


import com.petme.backend.model.Publicacion;
import com.petme.backend.model.TipoNotificacion;
import com.petme.backend.model.User;
import com.petme.backend.service.NotificacionesService;
import com.petme.backend.service.PublicacionService;
import com.petme.backend.service.UserService;
import com.petme.backend.exceptions.PublicacionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publicaciones")
@CrossOrigin(origins = "*")
public class PublicacionController {

    private final PublicacionService publicacionService;
    private final NotificacionesService notificacionesService;
    private final UserService userService;

    public PublicacionController(PublicacionService publicacionService, NotificacionesService notificacionesService, UserService userService) {
        this.publicacionService = publicacionService;
        this.notificacionesService = notificacionesService;
        this.userService = userService;
    }

    @GetMapping
    public List<Publicacion> findAllPublicaciones() {
        return publicacionService.getAllPublicaciones();
    }

   /* @PostMapping("/new-publicacion")
    public ResponseEntity<Publicacion> savePublicacion(@RequestBody Publicacion newPublicacion) {
        Publicacion saved = publicacionService.createPublicacion(newPublicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }*/

    @PostMapping("/new-publicacion")
    public ResponseEntity<?> savePublicacion(@RequestBody Publicacion newPublicacion) {
        try {
            publicacionService.createPublicacion(newPublicacion);

            // ESTO ARREGLA EL ERROR SIN INSTALAR NADA EXTRA:
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(java.util.Collections.singletonMap("mensaje", "Publicación creada con éxito"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(java.util.Collections.singletonMap("error", e.getMessage()));
        }
    }


    @GetMapping("/id-publicacion/{id}")
    public ResponseEntity<Publicacion> getById(@PathVariable Long id) {
        try {
            Publicacion pub = publicacionService.findById(id);
            return ResponseEntity.ok(pub);
        } catch (PublicacionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-publicacion/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            publicacionService.deletePublicacion(id);
            return ResponseEntity.noContent().build();
        } catch (PublicacionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-publicacion/{id}")
    public ResponseEntity<Publicacion> updateById(@RequestBody Publicacion publicacion, @PathVariable Long id) {
        try {
            Publicacion updated = publicacionService.updatePublicacion(publicacion, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
        } catch (PublicacionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // NUEVO: Endpoint para dar LIKE
    // POST /api/v1/publicaciones/{id}/like?userId=7
    @PostMapping("/{id}/like")
    public ResponseEntity<String> darLike(@PathVariable Long id, @RequestParam Long userId) {
        try {
            // A. Buscamos la publicación y quien da el like
            Publicacion publicacion = publicacionService.findById(id);
            User usuarioQueDaLike = userService.findById(userId);

            // B. Actualizamos el contador de likes (Lógica simple)
            publicacion.setLikes(publicacion.getLikes() + 1);
            publicacionService.updatePublicacion(publicacion, id);

            // C. --- AQUÍ VA EL CÓDIGO DE LA NOTIFICACIÓN ---
            // Solo notificamos si el que da like NO es el mismo dueño (para no auto-notificarse)
            if (!publicacion.getUsuario().getId().equals(userId)) {
                notificacionesService.crearNotificacion(
                        publicacion.getUsuario().getId(), // Dueño de la foto
                        TipoNotificacion.like,            // Tipo nuevo
                        "¡A alguien le gustó tu foto! ❤️",
                        usuarioQueDaLike.getUsername() + " le ha dado like a tu publicación."
                );
            }

            return ResponseEntity.ok("Like agregado y notificación enviada");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al dar like: " + e.getMessage());
        }
    }

    // NUEVO: Endpoint para COMENTAR
    // POST /api/v1/publicaciones/{id}/comentario?userId=7&texto=QueBonito
    @PostMapping("/{id}/comentario")
    public ResponseEntity<String> comentar(@PathVariable Long id,
                                           @RequestParam Long userId,
                                           @RequestParam String texto) {
        try {
            Publicacion publicacion = publicacionService.findById(id);
            User usuarioQueComenta = userService.findById(userId);

            // (Aquí iría la lógica para guardar el comentario en BD si tuvieras la tabla)
            // ejemplo: comentarioService.guardar(new Comentario(texto, ...));

            // --- AQUÍ VA EL CÓDIGO DE LA NOTIFICACIÓN ---
            if (!publicacion.getUsuario().getId().equals(userId)) {
                notificacionesService.crearNotificacion(
                        publicacion.getUsuario().getId(), // Al dueño de la publicación
                        TipoNotificacion.comentario,      // Tipo nuevo
                        "Nuevo comentario 💬",
                        usuarioQueComenta.getUsername() + " comentó: " + texto
                );
            }

            return ResponseEntity.ok("Comentario procesado y notificación enviada");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al comentar: " + e.getMessage());
        }
    }

    // GET: Obtener publicaciones de un usuario específico
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Publicacion>> getByUsuario(@PathVariable Long userId) {
        List<Publicacion> lista = publicacionService.getAllPublicaciones()
                .stream()
                // FILTRO DE SEGURIDAD:
                // 1. Verificamos que p.getUsuario() NO sea null
                // 2. Verificamos que el ID coincida
                .filter(p -> p.getUsuario() != null && p.getUsuario().getId().equals(userId))
                .toList();

        return ResponseEntity.ok(lista);
    }
}