package com.petme.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
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
    }

}
