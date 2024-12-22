package ru.vlsu.pet_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetEventDTO {
    private Long id;
    private PetEventTypeDTO type;
    private PetDTO pet;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Europe/Moscow")
    private LocalDate date;
    private PetUserDTO user;
}
