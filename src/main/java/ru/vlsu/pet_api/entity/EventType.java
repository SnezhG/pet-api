package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "event_type")
@Data
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
