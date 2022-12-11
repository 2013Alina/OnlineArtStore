package com.example.onlineartstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@ToString(exclude = {"comments"})
@EqualsAndHashCode(exclude = "comments")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(name = "username")
    private String username;
    @NonNull
    @Column(name = "password")
    private String password;
    @NonNull
    @Column(name = "enabled")
    private Boolean enabled;// это не поле а связь с таблицей авторизация



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}) //cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comment.setUser(this);
        comments.add(comment);
    }

}
