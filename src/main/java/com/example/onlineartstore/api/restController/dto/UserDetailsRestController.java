package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.api.dto.UserDetailDTO;
import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.entity.UserDetail;
import com.example.onlineartstore.repository.UserDetailRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/adminPageUsers/api/v3/userDetails")
@RestController
public class UserDetailsRestController {

    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    @GetMapping
    List<UserDetail> list() {
        return userDetailRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDetail> show(@PathVariable Integer id) {
        if (userDetailRepository.existsById(id)) {
            return ResponseEntity.of(userDetailRepository.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<UserDetail> update(@PathVariable Integer id, @RequestBody UserDetail userDetail) {
        Optional<UserDetail> foundUserDetail = userDetailRepository.findById(id);
        if (foundUserDetail.isPresent()) {
            UserDetail u = foundUserDetail.get();
            u.setFirstName(userDetail.getFirstName());
            u.setLastName(userDetail.getLastName());
            u.setBirthDate(userDetail.getBirthDate());
            u.setEmail(userDetail.getEmail());
            u.setTelephone(userDetail.getTelephone());
            return ResponseEntity.of(Optional.of(userDetailRepository.save(u)));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Validated UserDetailDTO userDetailDTO) {
        try {
            Optional<User> optionalUser = userRepository.findById(userDetailDTO.getUserId()); //Integer
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            UserDetail saved = userDetailRepository.save(userDetailDTO.toEntity(optionalUser.get()));
            return ResponseEntity
                    .created(URI.create("/adminPageUsers/api/v3/userDetails/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }


//    @PostMapping
//    ResponseEntity<?> create(@RequestBody UserDetail userDetail) {
//        try {
//            UserDetail saved = userDetailRepository.save(userDetail);
//            return ResponseEntity
//                    .created(URI.create("/adminPageUsers/api/v3/userDetails/" + saved.getId()))
//                    .build();
//        } catch (Throwable throwable) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(throwable);
//        }
//    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (userDetailRepository.existsById(id)) {
            userDetailRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
