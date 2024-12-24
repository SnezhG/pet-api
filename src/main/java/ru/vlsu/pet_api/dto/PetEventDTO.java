package ru.vlsu.pet_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PetEventDTO {
    private Long id;
    private PetEventTypeDTO type;
    private PetDTO pet;
    private String description;
    private String userToken;
    @JsonProperty
    private boolean isNotifEnabled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private LocalDateTime date;
    private PetUserDTO user;
}
