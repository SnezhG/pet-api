package ru.vlsu.pet_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.util.Date;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date birthDate;
    private String weight;
    private Blob photo;
    private String health;
    @ManyToOne
    @JoinColumn(name = "pet_user_id")
    private PetUser user;
    @OneToMany(mappedBy = "pet")
    private List<Activity> activities;
    @OneToMany(mappedBy = "pet")
    private List<Event> events;
}
