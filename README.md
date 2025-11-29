# 🐾 Pet-Me Backend

API RESTful desarrollada con **Spring Boot** para la red social de adopción de mascotas "Pet-Me". Este backend gestiona la autenticación de usuarios, perfiles, publicaciones de adopción, notificaciones y el registro de mascotas.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.5.7
* **Gestor de Dependencias:** Gradle
* **Base de Datos:** MySQL 8
* **Persistencia:** Spring Data JPA (Hibernate)
* **Seguridad:** Spring Security + JWT (JSON Web Tokens)
* **Encriptación:** BCrypt

## ⚙️ Configuración y Requisitos Previos

Para ejecutar este proyecto localmente, necesitas:

1.  **Java JDK 17** instalado.
2.  **MySQL** corriendo en el puerto `3306`.
3.  Clonar este repositorio:
    ```bash
    git clone [https://github.com/Dante-Cor/backend-petme.git](https://github.com/Dante-Cor/backend-petme.git)
    ```

### Configuración de Base de Datos
Asegúrate de configurar tus credenciales en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pet-me
Método,Endpoint,Descripción,Acceso
POST,/api/v1/users/new-user,Registrar nuevo usuario,Público
POST,/auth/login,Iniciar sesión (Obtener Token),Público
GET,/api/v1/users/id-user/{id},Ver perfil de usuario,🔒 Token
PUT,/api/v1/users/update-user/{id},Actualizar perfil,🔒 Token
DELETE,/api/v1/users/delete-user/{id},Eliminar cuenta,🔒 Token
Método,Endpoint,Descripción
POST,/api/v1/mascotas?userId={id},Registrar nueva mascota vinculada a un usuario
GET,/api/v1/mascotas,Ver todas las mascotas (Feed)
GET,/api/v1/mascotas/usuario/{id},Ver las mascotas de un usuario específico
Método,Endpoint,Descripción
POST,/api/v1/publicaciones/new-publicacion,Crear publicación de adopción
GET,/api/v1/publicaciones,Listar todas las publicaciones
GET,/api/v1/publicaciones/id-publicacion/{id},Ver detalle de una publicación
PUT,/api/v1/publicaciones/update-publicacion/{id},Editar publicación
DELETE,/api/v1/publicaciones/delete-publicacion/{id},Eliminar publicación
Método,Endpoint,Descripción
POST,/api/v1/adopciones/solicitar,Enviar solicitud de adopción (Estado: PENDIENTE)
GET,/api/v1/adopciones,Ver todas las solicitudes
GET,/api/v1/adopciones/{id},Ver una solicitud específica
PUT,/api/v1/adopciones/actualizar/{id},Aprobar/Rechazar solicitud
Método,Endpoint,Descripción
GET,/api/v1/notificaciones/usuario/{id},Ver notificaciones de un usuario
POST,/api/v1/notificaciones,Crear notificación (Sistema)
PUT,/api/v1/notificaciones/{id}/leer,Marcar como leída
DELETE,/api/v1/notificaciones/{id},Eliminar notificación
Método,Endpoint,Descripción
POST,/api/v1/fotos/new-foto,Subir foto vinculada a una mascota
GET,/api/v1/fotos,Ver galería de fotos
spring.datasource.username=TU_USUARIO (ej. root)
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
