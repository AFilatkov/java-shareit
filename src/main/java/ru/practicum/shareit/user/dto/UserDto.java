package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class UserDto {
    private String name;
    @Email(message = "Email должен быть корректным")
    private String email;
}
