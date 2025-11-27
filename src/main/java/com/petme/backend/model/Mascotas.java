package com.petme.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mascotas")
public class Mascotas {
    @Id // Pk Unica
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AI Y UQ
    @Column(name = "id_mascotas")
    private Long id_mascotas;

    // NN, UQ, VARCHAR(45)
    @Column(nullable = false, length = 45)
    private String nombre_mascotas;

    @Column(nullable = false, length = 45)
    private String especie;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false, length = 10)
    private String tamaño;

    @Column(nullable = false, length = 10)
    private String sexo;

    @Column(nullable = false, length = 250) // <--- Sin unique
    private String descripcion;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String foto_principal;

    @Column(nullable = false, length = 250)
    private String estado_adopcion;

    @Column(name = "fecha_publicacion", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fecha_publicacion;

    @Column(name = "fecha_actualizacion", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fecha_actualizacion;

    // ----- Relación de User a Mascotas 1:N ------
    /*@OneToMany
    @JoinColumn(name = "mascotas_id_user")
    private User users;*/

    // ✅ CÓDIGO CORREGIDO
// La relación es: Muchas mascotas -> Un usuario
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mascotas_id_user") // Asegúrate que esta columna exista en tu DB
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    //----Relación de Mascotas a Fotos Mascotas
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mascota")
    private List<FotosMascota> fotosMascotas= new ArrayList<>();



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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ... (tus otros métodos getters y setters) ...

    @PrePersist // <--- AGREGAR ESTO AL FINAL
    protected void onCreate() {
        if (this.fecha_publicacion == null) {
            this.fecha_publicacion = LocalDateTime.now();
        }
        if (this.fecha_actualizacion == null) {
            this.fecha_actualizacion = LocalDateTime.now();
        }
        // Valor por defecto si no viene
        if (this.estado_adopcion == null) {
            this.estado_adopcion = "DISPONIBLE";
        }
    }

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

