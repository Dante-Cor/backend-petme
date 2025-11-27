package com.petme.backend;

/*import com.petme.backend.model.User;
import com.petme.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner; // Importar esto */
import org.springframework.boot.SpringApplication;
/*import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar esto */
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import java.time.LocalDateTime; // Necesario para algunas fechas
import java.time.LocalDateTime;*/

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

    /*EL siguiente codigo implementar un usuario de prueba automático usando el CommandLineRunner de Spring Boot. Esto asegura que siempre habrá una credencial funcional la primera vez que arranquen la base de datos. */
    /*@Bean
    public CommandLineRunner initData(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            // Solo creamos al usuario si la tabla está vacía
            if (repo.count() == 0) {

                User admin = new User();

                // --- CAMPO CLAVE DE SEGURIDAD ---
                admin.setUsername("testuser");
                admin.setPassword(encoder.encode("test12345")); // La contraseña encriptada

                // --- CAMPOS MANDATORIOS DE TU ENTIDAD USER ---
                admin.setName("Admin");
                admin.setLastname("Project");
                admin.setEmail("admin@petme.com");
                admin.setRegisterDate(LocalDateTime.now().toString()); // Usamos string porque tu campo es String
                admin.setStatus(true);
                admin.setCity("Zapopan");
                // Asegúrate de llenar todos los campos @Column(nullable=false)

                repo.save(admin);
                System.out.println("--- USUARIO INICIAL 'testuser' CREADO ---");
            }
        };
    }*/

}
