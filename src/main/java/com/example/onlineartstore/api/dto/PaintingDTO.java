package com.example.onlineartstore.api.dto;

import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.entity.Author;
import com.example.onlineartstore.entity.Category;
import com.example.onlineartstore.entity.Painting;
import com.sun.istack.NotNull;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaintingDTO {
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private LocalDate published;

    private String imagePath;

    @NotNull
    @NotBlank
    private String size;

    @NotNull
    @NotBlank
    private String material;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private BigDecimal price;

    private Boolean sold;

    @NotNull
    private Integer categoryId;
    @NotNull
    private Integer authorId;
    @NotNull
    private Integer auctionId;

    public Painting toEntity(Category category, Author author, Auction auction) {
        Painting painting = new Painting(title, published, imagePath, size, material, description, price, sold);
        painting.setCategory(category);
        painting.setAuthor(author);
        painting.setAuction(auction);
        return painting;
    }
}
