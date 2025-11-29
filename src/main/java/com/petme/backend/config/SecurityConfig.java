package com.petme.backend.config;

import com.petme.backend.repository.UserRepository;
import com.petme.backend.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(crsf -> crsf.disable()) //Deshabilitar la seguridad de todas las peticiones externas
                // Configurar para que cualquier usuario pueda realizar peticiones
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                //Puede requerir un user y un password
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    // Codificar información (password)
    //passwordEncoder() debe inyectarse en UserService.class
    // e implementar en el metodo createUSer
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/

//============JwtFilter============//

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // 1. Definir cómo buscamos usuarios en la BD
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            com.petme.backend.model.User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Usuario no encontrado: " + username);
            }
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        };
    }

    // 2. Definir el encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. ¡LA PIEZA QUE FALTABA! Conectar UserDetailsService + PasswordEncoder
    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository)); // Usar nuestra lógica de BD
        provider.setPasswordEncoder(passwordEncoder()); // Usar BCrypt
        return provider;
    }

    // 4. Configurar el filtro de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authProvider) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(auth -> auth

                        // ======================================================
                        // 1. ZONA FRONTEND (¡ESTO ES LO NUEVO QUE NECESITAS!)
                        // ======================================================
                        // Permitir entrar a la página principal y archivos base
                        .requestMatchers("/", "/index.html", "/favicon.ico").permitAll()
                        // Permitir acceso a TUS carpetas de diseño (tal cual las tienes en IntelliJ)
                        .requestMatchers("/CSS/**", "/JavaScript/**", "/Img/**", "/componentes/**", "/templates/**").permitAll()

                        // ======================================================
                        // 2. ZONA BACKEND (LO QUE YA TENÍAS)
                        // ======================================================
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()

                        // NOTIFICACIONES
                        .requestMatchers(HttpMethod.POST, "/api/v1/notificaciones/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/notificaciones/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/notificaciones/**").authenticated()

                        // MASCOTAS
                        .requestMatchers(HttpMethod.POST, "/api/v1/mascotas/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/mascotas/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/mascotas/**").authenticated()
                        .requestMatchers("/api/v1/mascotas/buscar").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/mascotas/**").authenticated()

                        // PUBLICACIONES
                        .requestMatchers(HttpMethod.POST, "/api/v1/publicaciones/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/publicaciones/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/publicaciones/**").authenticated()

                        // ADOPCIONES
                        .requestMatchers(HttpMethod.POST, "/api/v1/adopciones/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/adopciones/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/adopciones/**").authenticated()

                        // FOTOS
                        .requestMatchers(HttpMethod.POST, "/api/v1/fotos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/fotos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/fotos/**").authenticated()

                        // BLOQUEAR TODO LO DEMÁS
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

