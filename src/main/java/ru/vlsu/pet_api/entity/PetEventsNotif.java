package ru.vlsu.pet_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "pet_events_notif")
@Data
@ToString(exclude = "petEvent")
public class PetEventsNotif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime time;
    private boolean notify;
    private String userToken;
    @OneToOne
    @JoinColumn(name = "pet_event_id", referencedColumnName = "id")
    private PetEvent petEvent;
}
