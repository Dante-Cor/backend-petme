package com.petme.backend.repository;

import com.petme.backend.model.Notificaciones;
import com.petme.backend.model.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.management.Notification;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {

    // 1. Obtener todas las notificaciones de un usuario
    List<Notificaciones> findAllByUser_id(Long userId);

    // 2. Obtener notificaciones sin leer de un usuario
    List<Notificaciones> findAllByUser_idAndLeidaIsFalse(Long userId);

    // 3. Obtener notificaciones de un tipo específico para un usuario
    List<Notificaciones> findAllByUser_idAndTipo(Long userId, TipoNotificacion tipo);

    // 4. Marcar todas las notificaciones de un usuario como leídas
    @Modifying
    @Query("UPDATE Notificaciones n SET n.leida = true WHERE n.user.id = :userId AND n.leida = false")
    int markAllAsRead(Long userId);
}
