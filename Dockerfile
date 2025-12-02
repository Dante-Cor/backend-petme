# --- Etapa 1: Compilar (Builder) ---
# Usamos una imagen JDK estándar (Eclipse Temurin) en lugar de la de Gradle
# Esto evita el conflicto de permisos de usuario vs root
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Copiamos todo el código fuente
COPY . .

# Damos permisos de ejecución al "wrapper" de Gradle (vital en Linux/Docker)
RUN chmod +x ./gradlew

# Ejecutamos el build usando tu propio wrapper
# Esto descargará Gradle automáticamente dentro de la imagen y compilará
RUN ./gradlew bootJar -x test --no-daemon

# --- Etapa 2: Ejecutar (Runner) ---
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiamos el JAR generado.
# Al usar el wrapper, la ruta suele ser build/libs/
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]