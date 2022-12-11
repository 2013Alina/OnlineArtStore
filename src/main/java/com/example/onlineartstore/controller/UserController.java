package com.example.onlineartstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userPage")
public class UserController {

    @GetMapping
    String index(){
        return "userPage";
    }
}
