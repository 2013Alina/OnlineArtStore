package com.example.onlineartstore.api.dto;

import com.example.onlineartstore.api.classAnnotation.Phone;
import com.example.onlineartstore.entity.*;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class UserDetailDTO {
    @NotNull
    @NotBlank
    @Length(min = 3)
    private String firstName;

    @NotNull
    @NotBlank
    @Length(min = 3)
    private String lastName;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    private String email;

    @NotNull
    @Phone
    private String telephone;

    @NotNull
    private Integer userId;

    public UserDetail toEntity(User user) {
        UserDetail userDetail = new UserDetail(firstName, lastName, birthDate, email, telephone);
        userDetail.setUser(user);
        return userDetail;
    }
}
