package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.api.dto.UserDetailDTO;
import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.entity.UserDetail;
import com.example.onlineartstore.error.ErrorResponse;
import com.example.onlineartstore.repository.UserDetailRepository;
import com.example.onlineartstore.repository.UserRepository;
import com.example.onlineartstore.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private final UserDetailService userDetailService;

    @GetMapping
    List<UserDetail> list() {
        return userDetailRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDetail> showUserDetail(@PathVariable Integer id) {
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


//    @PostMapping
//    ResponseEntity<?> createUserDetail(@RequestBody UserDetail userDetail, BindingResult bindingResult) {
//        if (!bindingResult.hasErrors()) {
//            String name = userDetail.getUser().getUsername();
//            Optional<User> optionalUser = userRepository.findUserByUsername(name);
//            if (optionalUser.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            UserDetail saved;
//            try {
//                saved = userDetailService.saveDetails(userDetail,name);
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(new ErrorResponse(e.getMessage()));
//            }
//            URI uri = URI.create("/adminPageUsers/api/v3/userDetails/" + saved.getId());
//            return ResponseEntity.created(uri).build();
//
//        }
//        return ResponseEntity.badRequest()
//                .body(bindingResult.getAllErrors());
//    }

    @PostMapping
    ResponseEntity<?> createUserDetail(@RequestBody @Validated UserDetailDTO userDetailDTO, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Optional<User> optionalUser = userRepository.findById(userDetailDTO.getUserId());
            UserDetail saved;
            try {
                if (optionalUser.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
                saved = userDetailRepository.save(userDetailDTO.toEntity(optionalUser.get()));
                saved = userDetailService.saveDetails(saved, optionalUser.get().getUsername());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(e.getMessage()));
            }
            URI uri = URI.create("/adminPageUsers/api/v3/userDetails/" + saved.getId());
            return ResponseEntity.created(uri).build();

        }
        return ResponseEntity.badRequest()
                .body(bindingResult.getAllErrors());
    }


    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (userDetailRepository.existsById(id)) {
            userDetailRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
