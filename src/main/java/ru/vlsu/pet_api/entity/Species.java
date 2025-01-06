package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "species")
@Data
@ToString(exclude = "pets")
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "species")
    private List<Pet> pets;
    @OneToMany(mappedBy = "species")
    private List<Breed> breeds;
}
