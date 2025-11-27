package com.petme.backend.controller;

import com.petme.backend.model.User;
import com.petme.backend.service.UserService;
import com.petme.backend.security.JwtUtil; // Ajusta el import si está en otro lado
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Importante para que el Frontend no tenga problemas
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService; // Inyectamos el Servicio
    private final PasswordEncoder passwordEncoder; // Inyectamos el Encoder para comparar contraseñas

    @Autowired
    public AuthController(JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        String identifier = loginUser.getUsername() != null ? loginUser.getUsername() : loginUser.getEmail();  // puede ser username o email
        String password = loginUser.getPassword();


        // 1. Buscamos al usuario usando el método existente en UserService
        User user = userService.findByUsername(identifier);
        //Por username
        if (user == null) {
            // intenta por email
            user = userService.findByEmail(identifier);
        }
        // 2. Validamos:
        //    a) Que el usuario o el email no sea null (exista)
        //    b) Que la contraseña enviada coincida con la encriptada en BD

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            // 3. Si todo es correcto, generamos y devolvemos el token
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(new TokenResponse(token, user.getId(), user.getUsername()));

        } else {
            // 4. Si falla, error 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }

    // Clase auxiliar pequeña para devolver el token como JSON { "token": "..." }
    // (Puedes ponerla aquí mismo abajo o en un archivo aparte)
    static class TokenResponse {
        private String token;
        private Long userId;      // <--- Agregado
        private String username;

        public TokenResponse(String token, Long userId, String username) {

            this.token = token;
            this.userId = userId;
            this.username = username;
        }

        public String getToken() { return token; }
        public Long getUserId() { return userId; }
        public String getUsername() { return username; }
    }
}
