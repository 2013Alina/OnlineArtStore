package com.example.onlineartstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String titleAuction;
    @NonNull
    private LocalDateTime startDate;
    @NonNull
    private LocalDateTime endDate;
    @NonNull
    private BigDecimal startingPrice;
    @NonNull
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "auction", cascade = {CascadeType.ALL})
    @ToString.Exclude //cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    //каскадное обновление данных, если author сохраняется, сохраняются м данные painting
    private List<Painting> paintingsAuction = new ArrayList<>();

    public void addPainting(Painting painting) {
        painting.setAuction(this);
        paintingsAuction.add(painting);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "auction", cascade = {CascadeType.ALL})
    @ToString.Exclude
    //cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    //каскадное обновление данных, если author сохраняется, сохраняются м данные painting
    private List<Bet> betsAuction = new ArrayList<>();

    public void addBet(Bet bet) {
        bet.setAuction(this);
        betsAuction.add(bet);
    }

    @ManyToMany(mappedBy = "participationInAuctions")
    @ToString.Exclude
    private Set<User> auctionParticipants = new HashSet<>();

    public void addAuctionParticipants(User user) {
        auctionParticipants.add(user);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "auction", cascade = {CascadeType.ALL})
    @ToString.Exclude
    // cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    private List<Comment> commentsAuction = new ArrayList<>();

    public void addComment(Comment comment) {
        comment.setAuction(this);
        commentsAuction.add(comment);
    }

}
