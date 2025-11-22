package com.petme.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "mascotas")
public class Mascotas {
    @Id // Pk Unica
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI Y UQ
    @Column(name = "id_mascotas")
    private Long id_mascotas;

    // NN, UQ, VARCHAR(45)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 45, message = "El nombre debe tener entre 2 y 45 caracteres")
    @Column(nullable = false, length = 45)
    private String nombre_mascotas;

    @Column(nullable = false, length = 45)
    private String especie;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 30, message = "La edad no puede superar 30 años")
    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false, length = 10)
    private String tamaño;

    @Column(nullable = false, length = 10)
    private String sexo;

    @Column(nullable = false, unique = true, length = 250)
    private String descripcion;

    @Column(nullable = false, unique = true, length = 250)
    private String foto_principal;

    @Column(nullable = false, length = 250)
    private String estado_adopcion;

    @CreatedDate
    @Column(name = "fecha_publicacion", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fecha_publicacion;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fecha_actualizacion;

    // ----- Relación de Mascotas a User (Muchas mascotas pertenecen a un usuario) ------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascotas_id_user", nullable = false)
    private User user;

    //----Relación de Mascotas a Fotos Mascotas
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota", orphanRemoval = true)
    private List<FotosMascota> fotosMascotas = new ArrayList<>();

    //----Relación de Mascotas a Publicaciones (Una mascota puede tener muchas publicaciones)
    @OneToMany(mappedBy = "mascota")
    private List<Publicacion> publicaciones = new ArrayList<>();

    //----Relación de Mascotas a Adopciones (Una mascota puede tener muchas solicitudes)
    @OneToMany(mappedBy = "mascota")
    private List<Adopcion> adopciones = new ArrayList<>();



    // --- Constructor
    public Mascotas(Long id_mascotas, String nombre_mascotas, String especie, Integer edad, String tamaño, String sexo, String descripcion, String foto_principal, String estado_adopcion, LocalDateTime fecha_publicacion, LocalDateTime fecha_actualizacion) {
        this.id_mascotas = id_mascotas;
        this.nombre_mascotas = nombre_mascotas;
        this.especie = especie;
        this.edad = edad;
        this.tamaño = tamaño;
        this.sexo = sexo;
        this.descripcion = descripcion;
        this.foto_principal = foto_principal;
        this.estado_adopcion = estado_adopcion;
        this.fecha_publicacion = fecha_publicacion;
        this.fecha_actualizacion = fecha_actualizacion;
    }

    // --- Constructor vacio para JPA
    public  Mascotas (){

    }

    //  Set and Get


    public Long getId_mascotas() {
        return id_mascotas;
    }

    public void setId_mascotas(Long id_mascotas) {
        this.id_mascotas = id_mascotas;
    }

    public String getNombre_mascotas() {
        return nombre_mascotas;
    }

    public void setNombre_mascotas(String nombre_mascotas) {
        this.nombre_mascotas = nombre_mascotas;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto_principal() {
        return foto_principal;
    }

    public void setFoto_principal(String foto_principal) {
        this.foto_principal = foto_principal;
    }

    public String getEstado_adopcion() {
        return estado_adopcion;
    }

    public void setEstado_adopcion(String estado_adopcion) {
        this.estado_adopcion = estado_adopcion;
    }

    public LocalDateTime getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(LocalDateTime fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public LocalDateTime getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(LocalDateTime fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    /*public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }*/

    // ---- ToString
    @Override
    public String toString() {
        return "Mascotas{" +
                "id_mascotas=" + id_mascotas +
                ", nombre_mascotas='" + nombre_mascotas + '\'' +
                ", especie='" + especie + '\'' +
                ", edad=" + edad +
                ", tamaño='" + tamaño + '\'' +
                ", sexo='" + sexo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto_principal='" + foto_principal + '\'' +
                ", estado_adopcion='" + estado_adopcion + '\'' +
                ", fecha_publicacion=" + fecha_publicacion +
                ", fecha_actualizacion=" + fecha_actualizacion +
                '}';
    }

    // ----- hashCode() and aquals()
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mascotas mascotas = (Mascotas) o;
        return Objects.equals(id_mascotas, mascotas.id_mascotas) && Objects.equals(nombre_mascotas, mascotas.nombre_mascotas) && Objects.equals(especie, mascotas.especie) && Objects.equals(edad, mascotas.edad) && Objects.equals(tamaño, mascotas.tamaño) && Objects.equals(sexo, mascotas.sexo) && Objects.equals(descripcion, mascotas.descripcion) && Objects.equals(foto_principal, mascotas.foto_principal) && Objects.equals(estado_adopcion, mascotas.estado_adopcion) && Objects.equals(fecha_publicacion, mascotas.fecha_publicacion) && Objects.equals(fecha_actualizacion, mascotas.fecha_actualizacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_mascotas, nombre_mascotas, especie, edad, tamaño, sexo, descripcion, foto_principal, estado_adopcion);
    }
}

