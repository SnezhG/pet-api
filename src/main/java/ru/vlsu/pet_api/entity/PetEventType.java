package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "pet_event_type")
@Data
@ToString(exclude = "petEvents")
public class PetEventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "type")
    private List<PetEvent> petEvents;
}
