package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.api.dto.UserDTO;
import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.error.ErrorResponse;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/adminPageUsers/api/v3/users")
@RestController
public class UsersRestController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    ResponseEntity<?> createUser(@RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) { //полный объект User для @OneToOne User and UserDetail
        if (!bindingResult.hasErrors()) {
            User saved = userDTO.toEntity();
            saved.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            try {
                saved = userRepository.save(saved);
                System.out.println("userId = " + saved.getId());

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(e.getMessage()));
            }
            URI uri = URI.create("/adminPageUsers/api/v3/users/" + saved.getId());
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest()
                .body(bindingResult.getAllErrors());
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
