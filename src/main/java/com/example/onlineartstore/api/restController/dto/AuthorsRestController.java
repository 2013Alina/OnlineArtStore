package com.example.onlineartstore.api.restController.dto;

import com.example.onlineartstore.entity.Author;
import com.example.onlineartstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adminPage/api/v1/authors")
@RequiredArgsConstructor
public class AuthorsRestController {

    private final AuthorRepository authorRepository;

    @GetMapping
    List<Author> list() {
        return authorRepository.findAll();
    }


    @GetMapping("/{id}")
    ResponseEntity<Author> showAuthor(@PathVariable Integer id) {
        if (authorRepository.existsById(id)) {
            return ResponseEntity.of(authorRepository.findById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody Author author) {
        try {
            Author saved = authorRepository.save(author);
            return ResponseEntity
                    .created(URI.create("/adminPage/api/v1/authors/" + saved.getId()))
                    .build();
        } catch (Throwable throwable) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(throwable);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author author) {
        Optional<Author> foundAuthors = authorRepository.findById(id);
        if (foundAuthors.isPresent()) {
            Author au = foundAuthors.get();
            au.setPseudonym(author.getPseudonym());
            au.setFirstName(author.getFirstName());
            au.setSurname(author.getSurname());
            au.setBirthDate(author.getBirthDate());
            au.setAwards(author.getAwards());
            au.setImagePath(au.getImagePath());
            return ResponseEntity.of(Optional.of(authorRepository.save(au)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}

