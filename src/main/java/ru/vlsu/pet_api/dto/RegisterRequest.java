package ru.vlsu.pet_api.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
