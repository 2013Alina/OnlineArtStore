package com.example.onlineartstore.service;

import com.example.onlineartstore.api.dto.BetDTO;
import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.entity.Bet;
import com.example.onlineartstore.entity.User;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.BetRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

//    public ResponseEntity<?> createNewBet(@PathVariable Integer id, @RequestBody BetDTO betDTO, Principal principal) {
//        Optional<Auction> optionalAuction = auctionRepository.findById(id);
//        if (optionalAuction.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        Auction auction = optionalAuction.orElseThrow();
//        LocalDateTime now = LocalDateTime.now();
//
//        Bet bet = new Bet();
//        BigDecimal money1 = new BigDecimal(String.valueOf(bet.getAmountMoney()));
//        BigDecimal money2 = new BigDecimal(String.valueOf(bet.getAmountMoney()));
//        BigDecimal currentMoney = new BigDecimal(String.valueOf(auction.getCurrentBet()));
//        BigDecimal startingMoney = new BigDecimal(String.valueOf(auction.getStartingPrice()));
//        if (currentMoney.compareTo(startingMoney) <= 0) {
//            return ResponseEntity.badRequest().build();
//        }else{
//            bet.setActive(true);
//            bet.setAmountMoney(currentMoney);
//            auction.setCurrentBet(currentMoney);
//        }
//        if (money2.compareTo(money1)<= 0) {
//            return ResponseEntity.badRequest().build();
//        }else{
//            bet.setActive(true);
//            bet.setAmountMoney(money2);
//            auction.setCurrentBet(money2);
//        }
//
//        if (auction.getEndDate().isBefore(now)) { //до конечной даты аукциона
//            return ResponseEntity.badRequest().build();
//        }
//
//        Optional<User> optionalUser = userRepository.findUserByUsername(principal.getName());
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        Bet saved = betRepository.save(betDTO.toEntity(optionalAuction.get(), optionalUser.get()));
//        return ResponseEntity
//                .created(URI.create("/api/v2/participateAuction/" + id + "/bet/" + saved.getId()))
//                .build();
//    }
}
