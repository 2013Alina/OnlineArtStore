package com.example.onlineartstore.api.dto;

import com.example.onlineartstore.entity.Author;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class AuthorDTO {
    @NotNull
    @NotBlank
    private String pseudonym;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String surname;
    @NotNull
    @NotBlank
    private LocalDate birthDate;
    @NotNull
    @NotBlank
    private String awards;

    private String imageLink;

    public Author toEntity(String imageLink) {
        Author author = new Author(pseudonym, firstName, surname, birthDate, awards, imageLink);
        return author;
    }
}
