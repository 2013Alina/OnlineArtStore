package com.example.onlineartstore.controller;

import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.PaintingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PaintingRepository paintingRepository;

    @GetMapping
    String generalPage(Model model) {
        model.addAttribute("paintings", paintingRepository.findAll());
        return "generalPage";
    }


    @GetMapping("/privatePage")
    String privatePage() {
        return "privatePage";
    }


}

