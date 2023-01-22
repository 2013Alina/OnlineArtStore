package com.example.onlineartstore.repository;

import com.example.onlineartstore.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Integer> {
}
