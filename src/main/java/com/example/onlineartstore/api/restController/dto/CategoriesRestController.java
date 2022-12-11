package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.entity.Category;
import com.example.onlineartstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adminPage/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesRestController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    List<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Category> showGroup(@PathVariable Integer id) {
        if (categoryRepository.existsById(id)) {
            return ResponseEntity.of(categoryRepository.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody Category category) {
        try {
            Category saved = categoryRepository.save(category);
            return ResponseEntity
                    .created(URI.create("/adminPage/api/v1/categories/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
        Optional<Category> foundCategories = categoryRepository.findById(id);
        if (foundCategories.isPresent()) {
            Category cat = foundCategories.get();
            cat.setName(category.getName());
            return ResponseEntity.of(Optional.of(categoryRepository.save(cat)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
