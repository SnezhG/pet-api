package ru.vlsu.pet_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date dateTime;
    private boolean isNotificationEnabled;
    @ManyToOne
    @JoinColumn(name = "pet_user_id")
    private PetUser user;
}
