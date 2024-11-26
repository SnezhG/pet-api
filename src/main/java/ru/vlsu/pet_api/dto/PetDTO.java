package ru.vlsu.pet_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Blob;
import java.util.Date;

@Data
public class PetDTO {
    private Long id;

    private String name;
    private SpeciesDTO species;
    private BreedDTO breed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date birthDate;
    private String weight;
    private Blob photo;
    private String health;
}
