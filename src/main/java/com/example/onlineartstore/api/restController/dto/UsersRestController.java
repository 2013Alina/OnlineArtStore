package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/adminPageUsers/api/v3/users")
@RestController
public class UsersRestController {

    private final UserRepository userRepository;

    @GetMapping
    List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<User> show(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            return ResponseEntity.of(userRepository.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            User u = foundUser.get();
            u.setUsername(user.getUsername());
            u.setPassword(user.getPassword());
            return ResponseEntity.of(Optional.of(userRepository.save(u)));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody User user) {
        try {
            User saved = userRepository.save(user);
            return ResponseEntity
                    .created(URI.create("/adminPageUsers/api/v3/users/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
