package ru.vlsu.pet_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pet")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;
    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;
    @ManyToOne
    @JoinColumn(name = "sex_id")
    private Sex sex;
    private LocalDate birthDate;
    private String weight;
    private String photo;
    private String health;
    @ManyToOne
    @JoinColumn(name = "pet_user_id")
    @JsonBackReference
    private PetUser user;
    @OneToMany(mappedBy = "pet")
    private List<PetEvent> petEvents;
}
