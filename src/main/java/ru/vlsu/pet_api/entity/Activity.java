package ru.vlsu.pet_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "activity")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType type;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone = "Europe/Moscow")
    private Date dateTime;
    private Blob photo;
    private Blob document;
    @ManyToOne
    @JoinColumn(name = "pet_user_id")
    private PetUser user;
}
