package com.example.onlineartstore.entity;

import com.example.onlineartstore.api.classAnnotation.Phone;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "UsersDetails")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @NonNull
    private String firstName;

    @NotNull
    @NotBlank
    @NonNull
    private String lastName;

    @NotNull
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private LocalDate birthDate;

    @NotNull
    @NotBlank
    @NonNull
    private String email;

    @NotNull
    @NotBlank
    @NonNull
    @Phone
    private String telephone;

    @OneToOne(mappedBy = "userDetail")
    User user;

}
