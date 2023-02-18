package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.api.dto.BetDTO;
import com.example.onlineartstore.entity.*;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.BetRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/v2/participateAuction") // /api/v2/participateAuction/{id}(аукциона)/bet/id(ставки)
@RestController
public class BetRestController {

    private final BetRepository betRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @GetMapping("{id}/bets")
    List<Bet> list(@PathVariable int id, Principal principal) {
        return auctionRepository.findById(id)
                .orElseThrow()
                .getBetsAuction()
                .stream()
                .filter(bet -> bet.getUser().getUsername().equals(principal.getName()))
                .sorted(Comparator.comparing(Bet::getCreatedDate).reversed())
                .toList();
    }

    @GetMapping("{auctionId}/bet/{id}")
    ResponseEntity<Bet> show(@PathVariable int auctionId, @PathVariable Integer id, Principal principal) {
        Optional<Bet> optionalBet = betRepository.findById(id);
        if (optionalBet.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Bet bet = optionalBet.get();
        if (bet.getAuction().getId() != auctionId) {
            return ResponseEntity.badRequest().build();
        }
        if (bet.getUser().getUsername().equals(principal.getName())) {
            return ResponseEntity.of(optionalBet);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Bet> update(@PathVariable Integer id, @RequestBody Bet bet) {
        Optional<Bet> foundBet = betRepository.findById(id);
        if (foundBet.isPresent()) {
            Bet bet1 = foundBet.get();
            bet1.setCreatedDate(bet.getCreatedDate());
            bet1.setAmountMoney(bet.getAmountMoney());
            bet1.setActive(bet.getActive());
            return ResponseEntity.of(Optional.of(betRepository.save(bet1)));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")  // /api/v2/participateAuction/id(аукциона)/bet/id(ставки)
    ResponseEntity<?> createBet(@PathVariable int id, @RequestBody BetDTO betDTO, Principal principal) {
        try {
            Optional<Auction> optionalAuction = auctionRepository.findById(id);
            if (optionalAuction.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Auction auction = optionalAuction.orElseThrow();
            LocalDateTime now = LocalDateTime.now();
            if(auction.getEndDate().isBefore(now)) { //до конечной даты аукциона
                return ResponseEntity.badRequest().build();
            }

            Optional<User> optionalUser = userRepository.findUserByUsername(principal.getName());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Bet saved = betRepository.save(betDTO.toEntity(optionalAuction.get(), optionalUser.get()));
            return ResponseEntity
                    .created(URI.create("/api/v2/participateAuction/" + id + "/bet/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }

//    @PostMapping("/{id}")
//    ResponseEntity<?> createBet(@PathVariable int id, @RequestBody BetDTO betDTO, Principal principal) {
//        try {
//            Optional<Auction> optionalAuction = auctionRepository.findById(id);
//            if (optionalAuction.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            Auction auction = optionalAuction.orElseThrow();
//            LocalDateTime now = LocalDateTime.now();
//
//            if(auction.getEndDate().isBefore(now)) { //до конечной даты аукциона
//                return ResponseEntity.badRequest().build();
//            }
//            Optional<User> optionalUser = userRepository.findUserByUsername(principal.getName());
//            if (optionalUser.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            Bet bet = betDTO.toEntity(optionalAuction.get(),optionalUser.get());
//            BigDecimal money1 = new BigDecimal(String.valueOf(bet.getAmountMoney()));
//            BigDecimal money2 = new BigDecimal(String.valueOf(bet.getAmountMoney()));
//            if (money2.compareTo(money1) <= 0) {
//                return ResponseEntity.badRequest().build();
//            }else{
//                auction.setCurrentBet(money2);
//            }
//
//            Bet saved = betRepository.save(bet);
//            return ResponseEntity
//                    .created(URI.create("/api/v2/participateAuction/" + id + "/bet/" + saved.getId()))
//                    .build();
//        } catch (Throwable throwable) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(throwable);
//        }
//    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (betRepository.existsById(id)) {
            betRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


