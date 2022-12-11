package com.example.onlineartstore.repository;

import com.example.onlineartstore.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

}
