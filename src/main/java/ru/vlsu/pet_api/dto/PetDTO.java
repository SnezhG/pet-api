package ru.vlsu.pet_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Blob;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDTO {
    private Long id;
    private String name;
    private SpeciesDTO species;
    private BreedDTO breed;
    private SexDTO sex;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate birthDate;
    private String weight;
    private Blob photo;
    private String health;
}
