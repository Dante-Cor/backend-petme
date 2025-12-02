# --- Etapa 1: Compilar (Builder) ---
FROM gradle:7.6.1-jdk17 AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .
# Creamos el JAR saltando los tests (vital para que no falle buscando la DB)
RUN gradle bootJar -x test --no-daemon

# --- Etapa 2: Ejecutar (Runner) ---
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]