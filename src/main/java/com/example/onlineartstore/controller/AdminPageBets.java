package com.example.onlineartstore.controller;

import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.PaintingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/adminPageBets")
public class AdminPageBets {
    private final AuctionRepository auctionRepository;
    private final PaintingRepository paintingRepository;

    @GetMapping
    String adminPageBets(Model model) {
        model.addAttribute("auctions", auctionRepository.findAll());
        model.addAttribute("paintings", paintingRepository.findAll());
        return "adminPageBets";
    }
}
