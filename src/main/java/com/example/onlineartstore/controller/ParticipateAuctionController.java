package com.example.onlineartstore.controller;

import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@RequestMapping("/participateAuction")
@RequiredArgsConstructor
@Controller
public class ParticipateAuctionController {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @GetMapping("/{id}")
    String index(@PathVariable Integer id, Model model) {
        model.addAttribute("auction", auctionRepository.findById(id).orElseThrow());
        model.addAttribute("auctionId", id);
        model.addAttribute("user", userRepository.findById(id));
        model.addAttribute("userId", id);
        return "participateAuction";
    }

    @GetMapping("/auctions/{id}")
    @ResponseBody
    public ResponseEntity<?> showAuction(@PathVariable Integer id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isPresent()) {
            return ResponseEntity.ok(optionalAuction.get());
        }
        return ResponseEntity.badRequest().body("Auction not found!");
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
}
