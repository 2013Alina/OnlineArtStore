package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.api.dto.PaintingDTO;
import com.example.onlineartstore.entity.Auction;
import com.example.onlineartstore.entity.Author;
import com.example.onlineartstore.entity.Category;
import com.example.onlineartstore.entity.Painting;
import com.example.onlineartstore.repository.AuctionRepository;
import com.example.onlineartstore.repository.AuthorRepository;
import com.example.onlineartstore.repository.CategoryRepository;
import com.example.onlineartstore.repository.PaintingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/adminPage/api/v1/paintings")
@RestController
public class PaintingsRestController {

    private final PaintingRepository paintingRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final AuctionRepository auctionRepository;

    // READ ALL Paintings
    @GetMapping
    List<Painting> list() {
        return paintingRepository.findAll();
    }

    // READ ONE Painting
    @GetMapping("/{id}")
    ResponseEntity<Painting> show(@PathVariable Integer id) {
        if (paintingRepository.existsById(id)) {
            return ResponseEntity.of(paintingRepository.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    // UPDATE ONE Painting
    @PutMapping("/{id}")
    ResponseEntity<Painting> update(@PathVariable Integer id, @RequestBody Painting painting) {
        Optional<Painting> foundPainting = paintingRepository.findById(id);
        if (foundPainting.isPresent()) {
            Painting s = foundPainting.get();
            s.setTitle(painting.getTitle());
            s.setPublished(painting.getPublished());
            s.setImagePath(painting.getImagePath());
            s.setSize(painting.getSize());
            s.setMaterial(painting.getMaterial());
            s.setDescription(painting.getDescription());
            s.setPrice(painting.getPrice());
            s.setSold(painting.getSold());
            return ResponseEntity.of(Optional.of(paintingRepository.save(s)));
        }
        return ResponseEntity.notFound().build();
    }

    // CREATE Painting
    @PostMapping
    ResponseEntity<?> create(@RequestBody PaintingDTO paintingDTO) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(paintingDTO.getCategoryId());
            Optional<Author> optionalAuthor = authorRepository.findById(paintingDTO.getAuthorId());
            Optional<Auction> optionalAuction = auctionRepository.findById(paintingDTO.getAuctionId());
            if (optionalCategory.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            if (optionalAuthor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            if (optionalAuction.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Painting saved = paintingRepository.save(paintingDTO.toEntity(optionalCategory.get(),optionalAuthor.get(),optionalAuction.get()));
            return ResponseEntity
                    .created(URI.create("/adminPage/api/v1/paintings/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }

        // DELETE Painting
        @DeleteMapping("/{id}")
        ResponseEntity<?> delete(@PathVariable Integer id) {
            if (paintingRepository.existsById(id)) {
                paintingRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }

}
