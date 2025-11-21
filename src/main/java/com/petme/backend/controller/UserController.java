package com.petme.backend.controller;

import com.petme.backend.exceptions.UserNotFoundException;
import com.petme.backend.model.User;
import com.petme.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //1. Traer a todos los users
    @GetMapping
    public List<User> findAllUsers() {
        return userService.getUsers();
    }

    //2. Postear a todos nuevos users (POST)
    @PostMapping("/new-user")
    public ResponseEntity<User> saveUser(@RequestBody User newUser) {
        User userByUsername = userService.findByUsername(newUser.getUsername());
        User userByEmail = userService.findByEmail(newUser.getEmail());

        if (userByUsername != null || userByEmail != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(newUser)); // 201
    }
    // 3. Traer a usuarios por ID (GET)
    @GetMapping("/id-user/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
    // 4. Borrar usuarios por ID (DELETE)
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
    // 5. Actualizar usuario por ID (UDPATE)
    // PUT update user by ID
    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateById(@RequestBody User user, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(user, id)); // 201
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }







}
