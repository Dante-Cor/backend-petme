package com.petme.backend.service;

import com.petme.backend.model.Notificaciones;
import com.petme.backend.model.TipoNotificacion;
import com.petme.backend.model.User;
import com.petme.backend.repository.NotificacionesRepository;
import com.petme.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotificacionesService {
    private final NotificacionesRepository notificacionesRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificacionesService(NotificacionesRepository notificacionesRepository, UserRepository userRepository) {
        this.notificacionesRepository = notificacionesRepository;
        this.userRepository = userRepository;
    }

    // --- Métodos Públicos Directos (Sin @Override) ---

    public Notificaciones crearNotificacion(Long userId, TipoNotificacion tipo, String titulo, String contenido) {
        User destinatario = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con ID: " + userId));

        Notificaciones nuevaNotificacion = new Notificaciones();
        nuevaNotificacion.setUser(destinatario);
        nuevaNotificacion.setTipo(tipo);
        nuevaNotificacion.setTitulo(titulo);
        nuevaNotificacion.setContenido(contenido);
        nuevaNotificacion.setFechaCreacion(LocalDateTime.now());
        nuevaNotificacion.setLeida(false);

        return notificacionesRepository.save(nuevaNotificacion);
    }

    public List<Notificaciones> obtenerTodasPorUsuario(Long userId) {
        return notificacionesRepository.findAllByUser_id(userId);
    }

    public List<Notificaciones> obtenerSinLeerPorUsuario(Long userId) {
        return notificacionesRepository.findAllByUser_idAndLeidaIsFalse(userId);
    }

    public Notificaciones marcarComoLeida(Long notificacionId) {
        Notificaciones notificacion = notificacionesRepository.findById(notificacionId)
                .orElseThrow(() -> new NoSuchElementException("Notificación no encontrada ID: " + notificacionId));

        if (!notificacion.isLeida()) {
            notificacion.setLeida(true);
            return notificacionesRepository.save(notificacion);
        }
        return notificacion;
    }

    @Transactional
    public void marcarTodasComoLeidas(Long userId) {
        notificacionesRepository.markAllAsRead(userId);
    }

    public Notificaciones obtenerPorId(Long notificacionId) {
        return notificacionesRepository.findById(notificacionId).orElse(null);
    }

}
