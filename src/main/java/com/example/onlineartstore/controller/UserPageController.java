package com.example.onlineartstore.controller;

import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.entity.UserDetail;
import com.example.onlineartstore.repository.UserDetailRepository;
import com.example.onlineartstore.repository.UserRepository;
import com.example.onlineartstore.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/userPage")
@RequiredArgsConstructor
public class UserPageController {

    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    @GetMapping
    String index(Model model) {
        model.addAttribute("userDetail", new UserDetail());
        return "userPage";
    }

//    @GetMapping("/{id}")
//    String index(Model model,@PathVariable Integer id ) {
//        model.addAttribute("userId", id);
//        model.addAttribute("userDetail", new UserDetail());
//        return "userPage";
//    }


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
