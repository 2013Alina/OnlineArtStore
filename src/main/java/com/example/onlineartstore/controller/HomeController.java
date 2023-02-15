package com.example.onlineartstore.controller;

import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.PaintingRepository;
import com.example.onlineartstore.repository.UserDetailRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final PaintingRepository paintingRepository;
    private final AuctionRepository auctionRepository;
    private final UserDetailRepository userDetailRepository;
    private final UserRepository userRepository;

    @GetMapping
    String generalPage(Model model) {
        model.addAttribute("paintings", paintingRepository.findAll());
        return "generalPage";
    }


    @GetMapping("/privatePage")
    String privatePage(Model model, Principal principal) {
        model.addAttribute("auctions", auctionRepository.findAll());
        model.addAttribute("paintings", paintingRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "privatePage";
    }


    @GetMapping("/infoPage")
    String infoPage(Model model) {
        model.addAttribute("userDetail", userDetailRepository.findAll());
        return "infoPage";
    }

}

