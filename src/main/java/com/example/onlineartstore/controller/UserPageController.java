package com.example.onlineartstore.controller;

import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.entity.UserDetail;
import com.example.onlineartstore.repository.UserDetailRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
@RequestMapping("/userPage")
@RequiredArgsConstructor
public class UserPageController {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    @GetMapping("/{id}")
    String index(@PathVariable Integer id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return "User not find!"; // знову питання.чи існує така в'юха. я гадаю, що ні.
        }
        User user = optionalUser.get();
        model.addAttribute("user", user);
        model.addAttribute("userId", id);
        if (user.getUserDetail() != null) {
            model.addAttribute("userDetail", userDetailRepository.getReferenceById(user.getUserDetail().getId()));
        } else {
            model.addAttribute("userDetail", new UserDetail());
        }
        return "userPage";
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<?> showUser(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.badRequest().body("User not found!");
    }

    @GetMapping("/userDetails/{id}")
    @ResponseBody
    public ResponseEntity<?> showUserDetail(@PathVariable Integer id) { // чому метод, що повертає UserDetail знаходиться в контролері UserPage?
        Optional<UserDetail> optionalUserDetail = userDetailRepository.findById(id);
        if (optionalUserDetail.isPresent()) {
            return ResponseEntity.ok(optionalUserDetail.get());
        }
        return ResponseEntity.badRequest().body("UserDetail not found!");
    }


}
