package com.example.onlineartstore.api.dto;

import com.example.onlineartstore.entity.Author;
import com.example.onlineartstore.entity.Category;
import com.example.onlineartstore.entity.Painting;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaintingDTO {

    private String tittle;
    private LocalDate published;
    private String imagePath;
    private String size;
    private String material;
    private String description;
    private BigDecimal price;

    private Integer categoryId;
    private Integer authorId;

    public Painting toEntity(Category category, Author author) {
        Painting painting = new Painting(tittle, published, imagePath, size, material, description, price);
        painting.setCategory(category);
        painting.setAuthor(author);
        return painting;
    }
}
