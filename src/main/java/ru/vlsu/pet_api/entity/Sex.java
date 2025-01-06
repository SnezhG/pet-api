package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "sex")
@Data
@ToString(exclude = "pets")
public class Sex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "sex")
    private List<Pet> pets;
}
