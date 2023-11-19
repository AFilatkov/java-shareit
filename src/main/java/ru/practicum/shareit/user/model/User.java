package ru.practicum.shareit.user.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class User {
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @Email
    @NotBlank
    @NotNull
    private String email;
}
