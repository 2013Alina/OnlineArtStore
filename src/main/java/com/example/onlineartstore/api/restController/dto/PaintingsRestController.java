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
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

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

    //CREATE Painting
//    @PostMapping
//    ResponseEntity<?> createPainting(@RequestBody PaintingDTO paintingDTO) {
//        try {
//            Optional<Category> optionalCategory = categoryRepository.findById(paintingDTO.getCategoryId());
//            Optional<Author> optionalAuthor = authorRepository.findById(paintingDTO.getAuthorId());
//            Optional<Auction> optionalAuction = auctionRepository.findById(paintingDTO.getAuctionId());
//            if (optionalCategory.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            if (optionalAuthor.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            if (optionalAuction.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            Painting saved = paintingRepository.save(paintingDTO.toEntity(optionalCategory.get(), optionalAuthor.get(), optionalAuction.get()));
//            return ResponseEntity
//                    .created(URI.create("/adminPage/api/v1/paintings/" + saved.getId()))
//                    .build();
//        } catch (Throwable throwable) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(throwable);
//        }
//    }

    @PostMapping
    public ResponseEntity<?> createPainting(@RequestParam("imageFile") MultipartFile imageFile,
                                            @ModelAttribute PaintingDTO paintingDTO) {
        try {
            String imagePath = paintingDTO.uploadImage(imageFile);

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

            Painting saved = paintingRepository.save(paintingDTO.toEntity(optionalCategory.get(), optionalAuthor.get(), optionalAuction.get(), imagePath));
            return ResponseEntity
                    .created(URI.create("/adminPage/api/v1/paintings/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }


    // UPDATE ONE Painting
    //http://localhost:8080/adminPage/api/v1/paintings/3 для update
//    @PutMapping("/{id}")
//    ResponseEntity<Painting> updatePainting(@PathVariable Integer id, @RequestBody @Validated PaintingDTO paintingDTO) {
//        Optional<Painting> foundPainting = paintingRepository.findById(id);
//        if (foundPainting.isPresent()) {
//            Painting variable = foundPainting.get();
//
//            Optional<Category> optionalCategory = categoryRepository.findById(paintingDTO.getCategoryId());
//            Optional<Author> optionalAuthor = authorRepository.findById(paintingDTO.getAuthorId());
//            Optional<Auction> optionalAuction = auctionRepository.findById(paintingDTO.getAuctionId());
//            if (optionalCategory.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            if (optionalAuthor.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            if (optionalAuction.isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//            Painting painting = paintingDTO.toEntity(optionalCategory.get(), optionalAuthor.get(), optionalAuction.get());
//
//            variable.setTitle(painting.getTitle());
//            variable.setPublished(painting.getPublished());
//            variable.setImagePath(painting.getImagePath());
//            variable.setSize(painting.getSize());
//            variable.setMaterial(painting.getMaterial());
//            variable.setDescription(painting.getDescription());
//            variable.setPrice(painting.getPrice());
//            variable.setSold(painting.getSold());
//            variable.setAuthor(painting.getAuthor());
//            variable.setAuction(painting.getAuction());
//            variable.setCategory(painting.getCategory());
//            return ResponseEntity.of(Optional.of(paintingRepository.save(variable)));
//        }
//        return ResponseEntity.notFound().build();
//    }

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
