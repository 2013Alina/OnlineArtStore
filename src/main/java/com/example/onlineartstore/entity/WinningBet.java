package com.example.onlineartstore.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "WinningBets")
public class WinningBet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private LocalDateTime createdDate;

    @OneToOne
    private Bet bet;

    @OneToOne
    private Auction auction;

    @OneToOne
    private User user;

}
