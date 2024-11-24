package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "breed")
@Data
public class Breed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
