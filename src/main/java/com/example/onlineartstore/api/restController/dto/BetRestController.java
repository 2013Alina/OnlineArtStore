package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.api.dto.BetDTO;
import com.example.onlineartstore.api.dto.PaintingDTO;
import com.example.onlineartstore.entity.*;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.BetRepository;
import com.example.onlineartstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/participateAuction/api/v2/bets")
@RestController
public class BetRestController {

    private final BetRepository betRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @GetMapping
    List<Bet> list() {
        return betRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Bet> show(@PathVariable Integer id) {
        if (betRepository.existsById(id)) {
            return ResponseEntity.of(betRepository.findById(id));
        }
        return ResponseEntity.notFound().build();
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

    @PostMapping
    ResponseEntity<?> createBet(@RequestBody BetDTO betDTO) {
        try {
            Optional<Auction> optionalAuction = auctionRepository.findById(betDTO.getAuctionId());
            Optional<User> optionalUser = userRepository.findById(betDTO.getUserId());
            if (optionalAuction.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Bet saved = betRepository.save(betDTO.toEntity(optionalAuction.get(), optionalUser.get()));
            return ResponseEntity
                    .created(URI.create("/participateAuction/api/v2/bets/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (betRepository.existsById(id)) {
            betRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


