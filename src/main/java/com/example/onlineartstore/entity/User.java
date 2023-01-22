package com.example.onlineartstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@ToString(exclude = {"betsUser","comments", "participationInAuctions"})
@EqualsAndHashCode(exclude = {"participationInAuctions", "roles"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @NonNull
    @Column(unique = true)
    @Length(min = 3)
    private String username;

    @NotNull
    @NotBlank
    @NonNull
    @Length(min = 6)
    private String password;

    @NonNull
    @NotNull
    private Boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    //cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comment.setUser(this);
        comments.add(comment);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    //cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}
    //каскадное обновление данных, если author сохраняется, сохраняются м данные painting
    private List<Bet> betsUser = new ArrayList<>();

    public void addBet(Bet bet) {
        bet.setUser(this);
        betsUser.add(bet);
    }
    @JsonIgnore
    @OneToOne
    UserDetail userDetail;

    @JsonIgnore
    @ManyToMany
    private Set<Auction> participationInAuctions = new HashSet<>();

    //добавила роли
    @JsonIgnore
    @ManyToMany(mappedBy = "usersRole", cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        role.addUserRole(this);
        roles.add(role);
    }

}
