package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/adminPageAuction/api/v2/auctions")
@RestController
public class AuctionRestController {

    private final AuctionRepository auctionRepository;

    @GetMapping
    List<Auction> list() {
        return auctionRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Auction> show(@PathVariable Integer id) {
        if (auctionRepository.existsById(id)) {
            return ResponseEntity.of(auctionRepository.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Auction> update(@PathVariable Integer id, @RequestBody Auction auction) {
        Optional<Auction> foundAuction = auctionRepository.findById(id);
        if (foundAuction.isPresent()) {
            Auction a = foundAuction.get();
            a.setTitle(auction.getTitle());
            a.setPublished(auction.getPublished());
            a.setStartingPrice(auction.getStartingPrice());
            a.setCurrentPrice(auction.getCurrentPrice());
            a.setDeadline(auction.getDeadline());
            a.setWinner(auction.getWinner());
            return ResponseEntity.of(Optional.of(auctionRepository.save(a)));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody Auction auction) {
        try {
            Auction saved = auctionRepository.save(auction);
            return ResponseEntity
                    .created(URI.create("/adminPageAuction/api/v2/auctions/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (auctionRepository.existsById(id)) {
            auctionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
