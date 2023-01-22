package com.example.onlineartstore.controller;

import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.PaintingRepository;
import com.example.onlineartstore.repository.UserDetailRepository;
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
    private final AuctionRepository auctionRepository;
    private final UserDetailRepository userDetailRepository;

    @GetMapping
    String generalPage(Model model) {
        model.addAttribute("paintings", paintingRepository.findAll());
        return "generalPage";
    }


    @GetMapping("/privatePage")
    String privatePage(Model model) {
        model.addAttribute("auctions", auctionRepository.findAll());
        return "privatePage";
    }

    @GetMapping("/infoPage")
    String infoPage(Model model) {
        model.addAttribute("userDetail", userDetailRepository.findAll());
        return "infoPage";
    }


}

