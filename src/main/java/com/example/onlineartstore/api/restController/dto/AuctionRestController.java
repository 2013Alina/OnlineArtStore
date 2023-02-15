package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.api.dto.AuctionDTO;
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
@RequestMapping("/adminPage/api/v1/auctions")
@RestController
public class AuctionRestController {

    private final AuctionRepository auctionRepository;

    @GetMapping
    List<Auction> list() {
        return auctionRepository.findAllWithParticipants();
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
            a.setTitleAuction(auction.getTitleAuction());
            a.setStartDate(auction.getStartDate());
            a.setEndDate(auction.getEndDate());
            a.setStartingPrice(auction.getStartingPrice());
            a.setCurrentBet(auction.getCurrentBet());
            a.setActive(auction.getActive());
            return ResponseEntity.of(Optional.of(auctionRepository.save(a)));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<?> createAuction(@RequestBody AuctionDTO auctionDTO) {
        try {
            Auction saved = auctionRepository.save(auctionDTO.toEntity());
            return ResponseEntity
                    .created(URI.create("/adminPage/api/v1/auctions/" + saved.getId()))
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
