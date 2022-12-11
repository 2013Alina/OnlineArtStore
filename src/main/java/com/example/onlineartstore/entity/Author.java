package com.example.onlineartstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String pseudonym;
    @NonNull
    private String firstName;
    @NonNull
    private String surname;
    @NonNull
    private LocalDate birthDate;
    @NonNull
    private String awards;
    @NonNull
    private String imagePath;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL}) //cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    //каскадное обновление данных, если author сохраняется, сохраняются м данные painting
    private List<Painting> paintingsAuthor = new ArrayList<>();

    public void addPainting(Painting... paintings) {
        for (Painting painting : paintings) {
            painting.setAuthor(this);
            this.paintingsAuthor.add(painting);
        }
    }

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comment> commentsAuthor = new ArrayList<>();

    public void addComment(Comment comment){
        comment.setAuthor(this);
        commentsAuthor.add(comment);
    }

}
