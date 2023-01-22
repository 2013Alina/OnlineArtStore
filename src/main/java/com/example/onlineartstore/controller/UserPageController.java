package com.example.onlineartstore.controller;

import com.example.onlineartstore.entity.UserDetail;
import com.example.onlineartstore.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/userPage")
@RequiredArgsConstructor
public class UserPageController {

    private final UserDetailRepository userDetailRepository;

    @GetMapping
    String index(Model model) {
        model.addAttribute("userDetail", new UserDetail());
        return "userPage";
    }


    @PostMapping
    String registration(@Validated @ModelAttribute UserDetail userDetail, BindingResult bindingResult, Model redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            userDetailRepository.save(userDetail);
            return "/userPage";
        }
        redirectAttributes.addAttribute("errors", bindingResult.getFieldErrors());
        redirectAttributes.addAttribute("userDetail", userDetail);

        return "/userPage";
    }
}
