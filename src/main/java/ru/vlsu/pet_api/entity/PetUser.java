package ru.vlsu.pet_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "pet_user")
@Data
public class PetUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Pet> pets;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<PetEvent> petEvents;

}
