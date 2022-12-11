package com.example.onlineartstore.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "Auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private LocalDateTime published;
    @NonNull
    private BigDecimal startingPrice;
    @NonNull
    private BigDecimal currentPrice;
    @NonNull
    private LocalDateTime deadline;
    @NonNull
    private String winner;
    // bet ставка отдельное поле ввода
    // участвовать в аукционе





}
