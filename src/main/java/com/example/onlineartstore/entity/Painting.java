package com.example.onlineartstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "Paintings")
@ToString(exclude = "comments")
@EqualsAndHashCode(exclude = "comments")
public class Painting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private LocalDate published;
    @NonNull
    private String imagePath;
    @NonNull
    private String size;
    @NonNull
    private String material;
    @NonNull
    private String description;
    @NonNull
    private BigDecimal price;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "painting", cascade = {CascadeType.ALL}) // cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment... comments) {
        for (Comment comment : comments) {
            comment.setPainting(this);
            this.comments.add(comment);
        }
    }


}
