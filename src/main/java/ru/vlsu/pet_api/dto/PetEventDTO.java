package ru.vlsu.pet_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PetEventDTO {
    private Long id;
    private PetEventTypeDTO type;
    private PetDTO pet;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date dateTime;
    private boolean isNotificationEnabled;
}
