package dev.viniciussr.gameslibrary.controller;

import dev.viniciussr.gameslibrary.dto.UserDTO;
import dev.viniciussr.gameslibrary.enums.Plans;
import dev.viniciussr.gameslibrary.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("/name")
    public ResponseEntity<List<UserDTO>> listUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.listUsersByName(name));
    }

    @GetMapping("/email")
    public ResponseEntity<List<UserDTO>> listUsersByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.listUsersByEmail(email));
    }

    @GetMapping("/plan")
    public ResponseEntity<List<UserDTO>> listUsersByPlan(@RequestParam Plans plan) {
        return ResponseEntity.ok(userService.listUsersByPlan(plan));
    }
}