package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "breed")
@Data
@ToString(exclude = "pets")
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "breed")
    private List<Pet> pets;
    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;
}
