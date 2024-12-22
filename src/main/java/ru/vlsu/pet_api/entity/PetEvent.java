package ru.vlsu.pet_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "pet_event")
@Data
public class PetEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pet_event_type_id")
    private PetEventType type;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    private String description;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "pet_user_id")
    @JsonBackReference
    private PetUser user;
}
