package com.example.onlineartstore.controller;

import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.entity.Author;
import com.example.onlineartstore.entity.Category;
import com.example.onlineartstore.entity.Painting;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.AuthorRepository;
import com.example.onlineartstore.repository.CategoryRepository;
import com.example.onlineartstore.repository.PaintingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@RequestMapping("/updatePainting")
@RequiredArgsConstructor
@Controller
public class UpdatePaintingController {

    private final PaintingRepository paintingRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final AuctionRepository auctionRepository;

    @GetMapping("/{id}")
    String index(@PathVariable Integer id, Model model) {
        model.addAttribute("painting", paintingRepository.findById(id).orElseThrow());
        model.addAttribute("paintingId", id);
        model.addAttribute("category", categoryRepository.findById(id).orElseThrow());
        model.addAttribute("author", authorRepository.findById(id).orElseThrow());
        model.addAttribute("auction", auctionRepository.findById(id).orElseThrow());
        return "updatePainting";
    }

    @GetMapping("/paintings/{id}")
    @ResponseBody
    public ResponseEntity<?> showPainting(@PathVariable Integer id) { // що повертає цей метод? категорію, автора, аукціон чи картину? тут порушен принцип єдиної відповідальності.
                                                                      // крім того ендпроін називається /paintings/
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return ResponseEntity.ok(optionalCategory.get());
        }
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return ResponseEntity.ok(optionalAuthor.get());
        }
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isPresent()) {
            return ResponseEntity.ok(optionalAuction.get());
        }
        Optional<Painting> optionalPainting = paintingRepository.findById(id);
        if (optionalPainting.isPresent()) {
            return ResponseEntity.ok(optionalPainting.get());
        }
        return ResponseEntity.badRequest().body("Painting not found!");
    }


}
