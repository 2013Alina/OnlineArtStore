package com.example.onlineartstore.controller;

import com.example.onlineartstore.repository.RoleRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/singup") //регистрация
@RequiredArgsConstructor
public class SingUpController {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

        @GetMapping
    String index() {
        return "singup";
    }

    @Data
    static class UsernamePassword {
        String username;
        String password;
    }

    @PostMapping   // <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/> обязательное поле для Post запроса
    String singup(@ModelAttribute UsernamePassword usernamePassword) {
        UserDetails user = User.builder()
                .username(usernamePassword.username)
                .password(passwordEncoder.encode(usernamePassword.password))
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);

        return "redirect:/login";
    }

}
