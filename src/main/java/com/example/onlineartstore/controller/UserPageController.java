package com.example.onlineartstore.controller;

import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.entity.UserDetail;
import com.example.onlineartstore.repository.UserDetailRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/userPage")
@RequiredArgsConstructor
public class UserPageController {

    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    String index(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow());
        return "userPage";
    }

    @GetMapping("/userDetails/{id}")
    @ResponseBody
    public ResponseEntity<?> showUserDetail(@PathVariable Integer id) {
        Optional<UserDetail> optionalUserDetail = userDetailRepository.findById(id);
        if (optionalUserDetail.isPresent()) {
            return ResponseEntity.ok(optionalUserDetail.get());
        }
        return ResponseEntity.badRequest().body("UserDetail not found!");
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


    @PostMapping("/{id}")
    String registration(@PathVariable int id, @Validated @ModelAttribute UserDetail userDetail, BindingResult bindingResult, Model redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            User user = userRepository.getReferenceById(id);
            userDetail.setUser(user);
            userDetailRepository.save(userDetail);
            return "/userPage";
        }
        redirectAttributes.addAttribute("errors", bindingResult.getFieldErrors());
        redirectAttributes.addAttribute("userDetail", userDetail);

        return "/userPage";
    }
}
