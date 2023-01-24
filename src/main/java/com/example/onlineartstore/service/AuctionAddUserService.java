package com.example.onlineartstore.service;

import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class AuctionAddUserService {

    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    @Transactional
    public Auction saveAuction(Auction auction, String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow();
        auction.addAuctionParticipants(user);
        auctionRepository.save(auction);
        userRepository.save(user);
        return auction;
    }
}

// Для @ManyToMany    (User and Auction) !!!