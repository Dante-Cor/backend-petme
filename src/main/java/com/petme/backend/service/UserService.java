package com.petme.backend.service;

import com.petme.backend.exceptions.UserNotFoundException;
import com.petme.backend.model.User;
import com.petme.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Obtener todos los usuarios
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 2. Crear nuevo usuario con contraseña encriptada
    public User createUser(User newUser) {
        String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        return userRepository.save(newUser);
    }

    // 3. Buscar por username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 4. Buscar por email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 5. Buscar por ID
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // 6. Eliminar usuario por ID
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    // 7. Actualizar usuario (CORREGIDO CON LÓGICA DEFENSIVA)
    public User updateUser(User updatedUser, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setLastname(updatedUser.getLastname());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setUsername(updatedUser.getUsername());

                    // --- INICIO CORRECCIÓN ---
                    // Solo encriptamos y actualizamos si viene una contraseña nueva válida
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    // Si viene nula, NO la tocamos (se queda la contraseña vieja que ya sirve)
                    // --- FIN CORRECCIÓN ---

                    existingUser.setTelephone(updatedUser.getTelephone());
                    existingUser.setCountry(updatedUser.getCountry());
                    existingUser.setCity(updatedUser.getCity());
                    existingUser.setPhotoProfile(updatedUser.getPhotoProfile());
                    // Opcional: La fecha de registro no debería cambiar al actualizar perfil
                    // existingUser.setRegisterDate(updatedUser.getRegisterDate());
                    existingUser.setStatus(updatedUser.getStatus());

                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /*// 7. Actualizar usuario completo por ID
    public User updateUser(User updatedUser, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setLastname(updatedUser.getLastname());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setUsername(updatedUser.getUsername());
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    existingUser.setTelephone(updatedUser.getTelephone());
                    existingUser.setCountry(updatedUser.getCountry());
                    existingUser.setCity(updatedUser.getCity());
                    existingUser.setPhotoProfile(updatedUser.getPhotoProfile());
                    existingUser.setRegisterDate(updatedUser.getRegisterDate());
                    existingUser.setStatus(updatedUser.getStatus());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }*/
}