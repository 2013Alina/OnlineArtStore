package com.example.onlineartstore.repository;

import com.example.onlineartstore.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    @Query("select a from Auction a left join fetch a.auctionParticipants")
    List<Auction> findAllWithParticipants();
}
