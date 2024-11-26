package ru.vlsu.pet_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "event")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType type;
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
