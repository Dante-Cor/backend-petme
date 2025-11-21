package com.petme.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notificaciones")
public class Notificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificaciones")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column (name = "tipo_notificacion", nullable = false)
    private TipoNotificacion tipo;

    @Column (nullable = false, length = 50)
    private String titulo;

    @Column (nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @Column (nullable = false)
    private boolean leida;

    @Column (name = "url_relacionada")
    private String urlRelacionada;

    @Column (name = "fecha_creacion", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fechaCreacion;

    //Relacion de Notificaciones a Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id_usuario", nullable = false)
    private User user;

    //Constructores
    public Notificaciones(Long id, TipoNotificacion tipo, String titulo, String contenido, boolean leida, String urlRelacionada, LocalDateTime fechaCreacion, User user) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.contenido = contenido;
        this.leida = leida;
        this.urlRelacionada = urlRelacionada;
        this.fechaCreacion = fechaCreacion;
        this.user = user;//Constructor de cardinalidad
    }
    //Constructor vacio para JPA
    public Notificaciones() {

    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public String getUrlRelacionada() {
        return urlRelacionada;
    }

    public void setUrlRelacionada(String urlRelacionada) {
        this.urlRelacionada = urlRelacionada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    //Getters y Setters de cardinalidad
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notificaciones{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", leida=" + leida +
                ", urlRelacionada='" + urlRelacionada + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Notificaciones that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}



