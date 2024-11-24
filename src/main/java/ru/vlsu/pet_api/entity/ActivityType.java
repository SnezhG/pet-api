package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "activity_type")
@Data
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
