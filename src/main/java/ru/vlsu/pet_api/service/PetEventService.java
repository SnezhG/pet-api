package ru.vlsu.pet_api.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.vlsu.pet_api.entity.PetEvent;

import java.time.LocalDate;
import java.util.List;

public interface PetEventService {
    PetEvent getById(Long id);

    List<PetEvent> getAllByUser(HttpServletRequest request);

    List<PetEvent> getAllOnWeekByUser(HttpServletRequest request);

    Long create(PetEvent petEvent, HttpServletRequest request);

    Long update(PetEvent newPetEvent);

    void delete(Long id);

    List<PetEvent> getAllByUserAndDate(LocalDate date, HttpServletRequest request);
}
